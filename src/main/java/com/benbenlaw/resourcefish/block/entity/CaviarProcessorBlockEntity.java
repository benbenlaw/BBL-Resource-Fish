package com.benbenlaw.resourcefish.block.entity;

import com.benbenlaw.core.block.entity.SyncableBlockEntity;
import com.benbenlaw.core.block.entity.handler.InputOutputItemHandler;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.CaviarItem;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.recipe.*;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CaviarProcessorBlockEntity extends SyncableBlockEntity {
    public final ContainerData data;
    private ActiveRecipeType activeRecipe = ActiveRecipeType.NONE;

    public int[] progress = new int[8];
    public int[] maxProgress = new int[8]; //

    public static final int[] INPUT_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7};
    public static final int[] UPGRADE_SLOTS = {8, 9, 10};
    public static final int[] OUTPUTS_SLOTS = {11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};

    private @NotNull RecipeInput getRecipeInput() {
        return new RecipeInput() {
            @Override
            public @NotNull ItemStack getItem(int index) {
                return itemHandler.getStackInSlot(index);
            }

            @Override
            public int size() {
                return itemHandler.getSlots();
            }
        };
    }

    public CaviarProcessorBlockEntity(BlockPos pos, BlockState state) {
        super(ResourceFishBlockEntities.CAVIAR_PROCESSOR_BLOCK_ENTITY.get(), pos, state);
        calculateMaxProgress();
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                if (index < 8) {
                    return CaviarProcessorBlockEntity.this.progress[index];
                } else if (index < 16) {
                    return CaviarProcessorBlockEntity.this.maxProgress[index - 8];
                } else {
                    return 0;
                }
            }

            @Override
            public void set(int index, int valve) {
                if (index < 8) {
                    CaviarProcessorBlockEntity.this.progress[index] = valve;
                } else if (index < 16) {
                    CaviarProcessorBlockEntity.this.maxProgress[index -8] = valve ;
                }
            }

            @Override
            public int getCount() {
                return 16;
            }
        };
    }

    public final FluidTank TANK = new FluidTank(8000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            sync();
        }
    };

    private final IFluidHandler fluidHandler = new IFluidHandler() {
        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return TANK.getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return TANK.getCapacity();
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return TANK.isFluidValid(stack);
        }


        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (resource.getFluid() == TANK.getFluid().getFluid() || TANK.isEmpty()) {
                return TANK.fill(resource, action);
            }
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (resource.getFluid() == TANK.getFluid().getFluid()) {
                return TANK.drain(resource.getAmount(), action);
            }
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (TANK.getFluidAmount() > 0) {
                return TANK.drain(maxDrain, action);
            }
            return FluidStack.EMPTY;
        }
    };

    public boolean onPlayerUse(Player player, InteractionHand hand) {
        return FluidUtil.interactWithFluidHandler(player, hand, TANK);
    }

    public IFluidHandler getFluidHandlerCapability(Direction side) {
        return fluidHandler;
    }
    public void sync() {
        if (level instanceof ServerLevel serverLevel) {
            LevelChunk chunk = serverLevel.getChunkAt(getBlockPos());
            if (Objects.requireNonNull(chunk.getLevel()).getChunkSource() instanceof ServerChunkCache chunkCache) {
                chunkCache.chunkMap.getPlayers(chunk.getPos(), false).forEach(this::syncContents);
            }
        }
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(23) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            sync();
            for (int upgradeSlot : UPGRADE_SLOTS) {
                if (slot == upgradeSlot) {
                    calculateMaxProgress();
                    return;
                }
            }
        }
    };

    private final IItemHandler caviarProcessorItemHandlerSide = new InputOutputItemHandler(itemHandler,
            (slot, stack) -> {
                for (int inputSlot : INPUT_SLOTS) {
                    if (slot == inputSlot) {
                        return true;
                    }
                }
                return false;
            },
            (slot) -> {
                for (int outputSlot : OUTPUTS_SLOTS) {
                    if (slot == outputSlot) {
                        return true;
                    }
                }
                return false;
            }
    );

    public IItemHandler getCaviarProcessorItemHandlerSide(Direction side) {
        return caviarProcessorItemHandlerSide;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemHandler;
    }

    public void tick() {

        assert level != null;
        if (!level.isClientSide()) {

            calculateMaxProgress();

            for (int i = 0; i < INPUT_SLOTS.length; i++) {
                int slot = INPUT_SLOTS[i];
                ItemStack inputStack = itemHandler.getStackInSlot(slot);

                if (inputStack.isEmpty()) {
                    progress[i] = 0;
                    continue;
                }

                RecipeInput singleSlotInput = new CaviarProcessorRecipeInput(itemHandler, fluidHandler) {
                    @Override
                    public ItemStack getItem(int index) {
                        if (index == slot) {
                            return inputStack;
                        }
                        return ItemStack.EMPTY;
                    }

                    @Override
                    public int size() {
                        return 1;
                    }
                };

                Optional<RecipeHolder<CaviarProcessorRecipe>> recipe = level.getRecipeManager().getAllRecipesFor(CaviarProcessorRecipe.Type.INSTANCE)
                        .stream().filter(match -> match.value().matches(singleSlotInput, level)).findFirst();

                if (recipe.isEmpty()) {
                    progress[i] = 0;
                    continue;
                }

                progress[i]++;

                if (progress[i] >= maxProgress[i]) {
                    fillOutputSlots(recipe.get().value());
                    itemHandler.extractItem(slot, 1, false);
                    progress[i] = 0;
                }
            }

        }

    }

    public void calculateMaxProgress() {

        int baseMaxProgress = 300;
        int totalReduction = 0;

        Map<Item, Integer> speedUpgrades = new HashMap<>();
        speedUpgrades.put(ResourceFishItems.SPEED_UPGRADE_1.get(), 40);
        speedUpgrades.put(ResourceFishItems.SPEED_UPGRADE_2.get(), 80);
        speedUpgrades.put(ResourceFishItems.SPEED_UPGRADE_3.get(), 120);

        ItemStack upgradeSlot1 = itemHandler.getStackInSlot(8);
        ItemStack upgradeSlot2 = itemHandler.getStackInSlot(9);
        ItemStack upgradeSlot3 = itemHandler.getStackInSlot(10);

        ItemStack[] upgradeSlots = {upgradeSlot1, upgradeSlot2, upgradeSlot3};

        for (ItemStack upgradeSlot : upgradeSlots ) {
            if (upgradeSlot.isEmpty()) continue;

            Item upgradeItem = upgradeSlot.getItem();
            if (speedUpgrades.containsKey(upgradeItem)) {
                totalReduction += speedUpgrades.get(upgradeItem);
            }
        }

        Arrays.fill(maxProgress, Math.max(20, baseMaxProgress - totalReduction));
    }

    public void fillOutputSlots(CaviarProcessorRecipe recipe) {
        assert level != null;

        List<ItemStack> results = recipe.rollResults(level.random);

        for (ItemStack result : results) {
            for (int outputSlot : OUTPUTS_SLOTS) {
                ItemStack slotStack = itemHandler.getStackInSlot(outputSlot);
                if (slotStack.isEmpty()) {
                    itemHandler.setStackInSlot(outputSlot, result.copy());
                    result.setCount(0);
                    break;
                } else if (ItemStack.isSameItem(slotStack, result)) {
                    int maxStackSize = slotStack.getMaxStackSize();
                    int spaceLeft = maxStackSize - slotStack.getCount();
                    if (spaceLeft > 0) {
                        int toAdd = Math.min(spaceLeft, result.getCount());
                        slotStack.grow(toAdd);
                        result.shrink(toAdd);
                        if (result.isEmpty()) {
                            break;
                        }
                    }
                }
            }
        }

        if (!recipe.fluidStack().isEmpty()) {
            fluidHandler.fill(recipe.fluidStack(), IFluidHandler.FluidAction.EXECUTE);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.put("inventory", itemHandler.serializeNBT(provider));
        compoundTag.put("tank", TANK.writeToNBT(provider, new CompoundTag()));
        compoundTag.putIntArray("progress", progress);
        compoundTag.putIntArray("maxProgress", maxProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        this.itemHandler.deserializeNBT(provider, compoundTag.getCompound("inventory"));
        this.TANK.readFromNBT(provider, compoundTag.getCompound("tank"));
        progress = compoundTag.getIntArray("progress");
        maxProgress = compoundTag.getIntArray("maxProgress");
        super.loadAdditional(compoundTag, provider);

    }


    private int insertStackIntoSlots(ItemStackHandler itemHandler, ItemStack stack, int[] slots) {
        // Try to merge into existing stacks first
        for (int slot : slots) {
            ItemStack slotStack = itemHandler.getStackInSlot(slot);
            if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, stack)) {
                int maxStackSize = slotStack.getMaxStackSize();
                int spaceLeft = maxStackSize - slotStack.getCount();
                if (spaceLeft > 0) {
                    int toAdd = Math.min(spaceLeft, stack.getCount());
                    slotStack.grow(toAdd);
                    stack.shrink(toAdd);
                    if (stack.isEmpty()) return 0; // fully inserted
                }
            }
        }
        // Then try empty slots
        for (int slot : slots) {
            ItemStack slotStack = itemHandler.getStackInSlot(slot);
            if (slotStack.isEmpty()) {
                int toAdd = Math.min(stack.getMaxStackSize(), stack.getCount());
                itemHandler.setStackInSlot(slot, stack.copyWithCount(toAdd));
                stack.shrink(toAdd);
                if (stack.isEmpty()) return 0; // fully inserted
            }
        }
        return stack.getCount(); // return leftover count if any
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

}

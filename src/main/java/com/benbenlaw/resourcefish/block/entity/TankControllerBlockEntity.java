package com.benbenlaw.resourcefish.block.entity;

import com.benbenlaw.core.block.entity.SyncableBlockEntity;
import com.benbenlaw.core.block.entity.handler.InputOutputItemHandler;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.recipe.ActiveRecipeType;
import com.benbenlaw.resourcefish.recipe.FishBreedingRecipe;
import com.benbenlaw.resourcefish.recipe.FishInfusingRecipe;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TankControllerBlockEntity extends SyncableBlockEntity {
    public final ContainerData data;
    private ActiveRecipeType activeRecipe = ActiveRecipeType.NONE;
    public static final int RECIPE_SLOT_1 = 12;
    public static final int RECIPE_SLOT_2 = 13;
    public static final int RECIPE_SLOT_3 = 14;
    public static final int UPGRADE_SLOT_1 = 15;
    public static final int UPGRADE_SLOT_2 = 16;
    public static final int UPGRADE_SLOT_3 = 17;
    public static final int UPGRADE_SLOT_4 = 18;
    public static int progress = 0;
    public static int maxProgress = Integer.MAX_VALUE; //
    private boolean inventoryChanged = true;
    public static List<ResourceFishEntity> fishPool = new ArrayList<>();
    private final Set<UUID> previouslyAllowedFish = new HashSet<>();

    private RecipeInput cachedInventory = getRecipeInput();

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

    public TankControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ResourceFishBlockEntities.TANK_CONTROLLER_BLOCK_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0; // Default case if index is out of bounds
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> progress = value;
                    case 1 -> maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2; // Placeholder for actual count
            }
        };
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(19) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            sync();
            inventoryChanged = true;
            activeRecipe = ActiveRecipeType.NONE;
        }
    };

    private final IItemHandler tankControllerItemHandlerSide = new InputOutputItemHandler(
            itemHandler,
            (slot, stack) -> slot >= 12 && slot <= 15,
            (slot) -> slot >= 0 && slot <= 11
    );

    public IItemHandler getTankControllerItemHandlerSide(Direction side) {
        return tankControllerItemHandlerSide;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemHandler;
    }

    public void tick() {
        Direction facing = this.getBlockState().getValue(BlockStateProperties.FACING);
        Direction opposite = facing.getOpposite();
        BlockPos centerPos = this.worldPosition.relative(opposite, 3);

        AABB box = calculateBox(centerPos, opposite);

        if (level.isClientSide()) {
            spawnBoxOutlineParticles(box);
            return; // Skip processing on the client side
        }

        if (inventoryChanged) {
            cachedInventory = getRecipeInput();;
            inventoryChanged = false;
        }

        Optional<RecipeHolder<FishBreedingRecipe>> breedingMatch = level.getRecipeManager()
                .getRecipeFor(FishBreedingRecipe.Type.INSTANCE, cachedInventory, level);

        Optional<RecipeHolder<FishInfusingRecipe>> infusingMatch = level.getRecipeManager()
                .getRecipeFor(FishInfusingRecipe.Type.INSTANCE, cachedInventory, level);

        if (Objects.requireNonNull(activeRecipe) == ActiveRecipeType.NONE) {
            if (breedingMatch.isPresent()) {
                activeRecipe = ActiveRecipeType.BREEDING;
            } else if (infusingMatch.isPresent()) {
                activeRecipe = ActiveRecipeType.INFUSING;
            }
        }


        switch (activeRecipe) {
            case BREEDING -> breedingRecipe(breedingMatch, centerPos, itemHandler, level);
            case INFUSING -> infusingRecipe(infusingMatch, centerPos, itemHandler, level);
            default -> {
                progress = 0;
                maxProgress = Integer.MAX_VALUE;
                activeRecipe = ActiveRecipeType.NONE;
            }
        }

        //Collecting items
        assert level != null;
        List<Entity> entityList = level.getEntities(null, box);

        int maxSlots = 12;
        int[] inputSlots = new int[maxSlots];
        for (int i = 0; i < maxSlots; i++) {
            inputSlots[i] = i;
        }

        if (level.getGameTime() % 20 == 0) {
            fishPool.clear();

            Set<UUID> currentAllowedFish = new HashSet<>();
            for (Entity entity : entityList) {
                if (entity instanceof ResourceFishEntity fish) {
                    fishPool.add(fish);
                    fish.setAllowedToDrop(true);
                    currentAllowedFish.add(fish.getUUID());
                }
            }

            for (UUID uuid : previouslyAllowedFish) {
                if (!currentAllowedFish.contains(uuid)) {
                    Entity entity = ((ServerLevel) level).getEntity(uuid);
                    if (entity instanceof ResourceFishEntity fish) {
                        fish.setAllowedToDrop(false);
                    }
                }
            }

            previouslyAllowedFish.clear();
            previouslyAllowedFish.addAll(currentAllowedFish);

            for (Entity entity : entityList) {
                if (entity instanceof ItemEntity itemEntity) {
                    ItemStack stack = itemEntity.getItem().copy();

                    int leftoverCount = insertStackIntoSlots(itemHandler, stack, inputSlots);

                    if (leftoverCount == 0) {
                        itemEntity.discard();
                    } else {
                        itemEntity.setItem(stack.split(leftoverCount));
                    }
                }
            }

        }
    }

    private AABB calculateBox(BlockPos centerPos, Direction direction) {

        int widthUpgradeAmount = 0;
        int depthUpgradeAmount = 0;


        for (int i = UPGRADE_SLOT_1; i <= UPGRADE_SLOT_4; i++) {
            ItemStack upgradeStack = itemHandler.getStackInSlot(i);
            if (upgradeStack.isEmpty()) continue;

            Item item = upgradeStack.getItem();

            if (item == ResourceFishItems.DEPTH_UPGRADE_1.get()) {
                depthUpgradeAmount+= 1;
            } else if (item == ResourceFishItems.DEPTH_UPGRADE_2.get()) {
                depthUpgradeAmount+= 2;
            } else if (item == ResourceFishItems.DEPTH_UPGRADE_3.get()) {
                depthUpgradeAmount+= 3;
            } else if (item == ResourceFishItems.WIDTH_UPGRADE_1.get()) {
                widthUpgradeAmount+= 1;
            }  else if (item == ResourceFishItems.WIDTH_UPGRADE_2.get()) {
                widthUpgradeAmount+= 2;
            } else if (item == ResourceFishItems.WIDTH_UPGRADE_3.get()) {
                widthUpgradeAmount+= 3;
            }
        }

        BlockPos newCenterPositionWidth = centerPos.relative(direction, widthUpgradeAmount);
        BlockPos newCenterPositionDepth = centerPos.relative(direction, depthUpgradeAmount);


        int minX = newCenterPositionWidth.getX() - 1 - widthUpgradeAmount;
        int minZ = newCenterPositionWidth.getZ() - 1 - widthUpgradeAmount;
        int maxX = newCenterPositionWidth.getX() + 2 + widthUpgradeAmount;
        int maxZ = newCenterPositionWidth.getZ() + 2 + widthUpgradeAmount;
        int minY = newCenterPositionDepth.getY() - 1 - depthUpgradeAmount;
        int maxY = newCenterPositionDepth.getY() + 2 + depthUpgradeAmount;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    private static void breedingRecipe(Optional<RecipeHolder<FishBreedingRecipe>> match, BlockPos centerPos, ItemStackHandler itemHandler, Level level) {
        if (match.isPresent()) {
            maxProgress = match.get().value().duration();
            progress++;

            if (progress >= maxProgress) {
                itemHandler.getStackInSlot(RECIPE_SLOT_1).shrink(match.get().value().breedingIngredient().count());

                boolean chanceSuccess = level.random.nextDouble() < match.get().value().chance();

                if (chanceSuccess) {
                    ResourceFishEntity fish = ResourceFishEntities.RESOURCE_FISH.get().create(level);
                    fish.setResourceType(ResourceType.get(match.get().value().createdFish()));
                    fish.setPos(Vec3.atCenterOf(centerPos));
                    level.addFreshEntity(fish);
                } else {
                    ItemEntity bones = new ItemEntity(level, centerPos.getX() + 0.5, centerPos.getY() + 1, centerPos.getZ() + 0.5,
                            new ItemStack(Items.BONE));
                    bones.setPos(Vec3.atCenterOf(centerPos));
                    level.addFreshEntity(bones);
                }
                progress = 0;
                maxProgress = Integer.MAX_VALUE;
            }
        } else {
            progress = 0;
            maxProgress = Integer.MAX_VALUE;
        }
    }
    private static void infusingRecipe(Optional<RecipeHolder<FishInfusingRecipe>> match, BlockPos centerPos, ItemStackHandler itemHandler, Level level) {
        if (match.isPresent()) {
            maxProgress = match.get().value().duration();
            progress++;

            if (progress >= maxProgress) {

                itemHandler.getStackInSlot(RECIPE_SLOT_1).shrink(match.get().value().input1().count());
                itemHandler.getStackInSlot(RECIPE_SLOT_2).shrink(match.get().value().input2().count());
                itemHandler.getStackInSlot(RECIPE_SLOT_3).shrink(match.get().value().input3().count());

                boolean chanceSuccess = level.random.nextDouble() < match.get().value().chance();

                if (chanceSuccess) {

                    for (ResourceFishEntity fish  : TankControllerBlockEntity.fishPool) {

                        System.out.println(fish.getResourceType().getId());
                        System.out.println(match.get().value().fish());

                        if (fish.getResourceType().getId().equals(match.get().value().fish())) {
                            fish.setResourceType(ResourceType.get(match.get().value().createdFish()));
                            break;
                        }
                    }
                } else {
                    ItemEntity bones = new ItemEntity(level, centerPos.getX() + 0.5, centerPos.getY() + 1, centerPos.getZ() + 0.5,
                            new ItemStack(Items.BONE));
                    bones.setPos(Vec3.atCenterOf(centerPos));
                    level.addFreshEntity(bones);
                }
                progress = 0;
                maxProgress = Integer.MAX_VALUE;
            }
        } else {
            progress = 0;
            maxProgress = Integer.MAX_VALUE;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);
        compoundTag.put("inventory", itemHandler.serializeNBT(provider));
        compoundTag.putInt("progress", progress);
        compoundTag.putInt("maxProgress", maxProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        this.itemHandler.deserializeNBT(provider, compoundTag.getCompound("inventory"));
        super.loadAdditional(compoundTag, provider);
        progress = compoundTag.getInt("progress");
        maxProgress = compoundTag.getInt("maxProgress");
    }

    private void spawnBoxOutlineParticles(AABB box) {
        if (level.isClientSide) {
            for (double x = box.minX; x <= box.maxX; x += 0.25) {
                for (double y = box.minY; y <= box.maxY; y += 0.25) {
                    for (double z = box.minZ; z <= box.maxZ; z += 0.25) {
                        // Spawn particles only at the edges of the box
                        boolean onXEdge = x == box.minX || x == box.maxX;
                        boolean onYEdge = y == box.minY || y == box.maxY;
                        boolean onZEdge = z == box.minZ || z == box.maxZ;

                        if ((onXEdge && onYEdge) || (onXEdge && onZEdge) || (onYEdge && onZEdge)) {
                            level.addParticle(ParticleTypes.END_ROD, x, y, z, 0, 0, 0);
                        }
                    }
                }
            }
        }
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

package com.benbenlaw.resourcefish.screen;

import com.benbenlaw.core.screen.util.CoreSlotTextures;
import com.benbenlaw.core.screen.util.slot.CoreSlot;
import com.benbenlaw.core.screen.util.slot.ResultSlot;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.block.entity.CaviarProcessorBlockEntity;
import com.benbenlaw.resourcefish.block.entity.TankControllerBlockEntity;
import com.benbenlaw.resourcefish.util.ResourceFishTags;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerCopySlot;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;


public class CaviarProcessorMenu extends AbstractContainerMenu {
    protected CaviarProcessorBlockEntity blockEntity;
    protected Level level;
    protected ContainerData data;
    protected Player player;
    protected BlockPos blockPos;
    public final Map<Integer, Point> slotPositions = new HashMap<>();

    public CaviarProcessorMenu(int containerID, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerID, inventory, extraData.readBlockPos(), new SimpleContainerData(16));
    }

    public CaviarProcessorMenu(int containerID, Inventory inventory, BlockPos blockPos, ContainerData data) {
        super(ResourceFishMenuTypes.CAVIAR_PROCESSOR_MENU.get(), containerID);
        this.player = inventory.player;
        this.blockPos = blockPos;
        this.level = inventory.player.level();
        this.blockEntity = (CaviarProcessorBlockEntity) this.level.getBlockEntity(blockPos);
        this.data = data;

        CaviarProcessorBlockEntity entity = (CaviarProcessorBlockEntity) this.level.getBlockEntity(blockPos);

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        assert entity != null;

        //Slots
        int xStart = 8;
        int yStart = 17;
        int xOffset = 18;
        int yOffset = 18;
        // Input Slots
        for (int i = 0; i < 8; i++) {
            createRecipeSlot(entity, i, xStart + (i % 4) * xOffset, yStart + (i / 4) * yOffset);
            slotPositions.put(i, new Point(xStart + (i % 4) * xOffset, yStart + (i / 4) * yOffset));
        }
        // Upgrade Slots
        createUpgradeSlot(entity, 8, 17, 53);
        createUpgradeSlot(entity, 9, 35, 53);
        createUpgradeSlot(entity, 10, 53, 53);
        // Output Slots
        int xStartOutput = 98;
        int yStartOutput = 17;
        int slotCount = 11;
        int totalSlots = 3 * 4; // 12

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                int currentSlot = row * 4 + col;
                if (currentSlot == totalSlots - 1) continue; // skip last one

                int xPos = xStartOutput + (col * xOffset);
                int yPos = yStartOutput + (row * yOffset);
                this.addSlot(new ResultSlot(entity.getItemStackHandler(), slotCount++, xPos, yPos, 64));
            }
        }


        addDataSlots(data);
    }

    private void createUpgradeSlot(CaviarProcessorBlockEntity entity, int slot, int x, int y) {
        this.addSlot(new CoreSlot(entity.getItemStackHandler(), slot, x, y) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, CoreSlotTextures.UPGRADE_SLOT);
            }
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(ResourceFishTags.Items.UPGRADES);
            }
        });
    }

    private void createRecipeSlot(CaviarProcessorBlockEntity entity, int slot, int x, int y) {
        this.addSlot(new SlotItemHandler(entity.getItemStackHandler(), slot, x, y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                // Reject items that are upgrades
                return !stack.is(ResourceFishTags.Items.UPGRADES);
            }
        });
    }

    public boolean isCrafting() {
        return data.get(0) > 0 ;
    }

    public int getScaledProgress(int slotIndex) {
        int progress = this.data.get(slotIndex);
        int maxProgress = this.data.get(slotIndex + 8); // Assuming max progress is stored in the next slot
        int progressBarWidth = 19;
        return maxProgress != 0 && progress != 0 ? progress * progressBarWidth / maxProgress : 0;
    }


    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;



    private static final int TE_INVENTORY_SLOT_COUNT = 22;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), blockPos),
                player, ResourceFishBlocks.CAVIAR_PROCESSOR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

}

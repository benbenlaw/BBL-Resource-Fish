package com.benbenlaw.resourcefish.block.entity;


import com.benbenlaw.core.block.entity.SyncableBlockEntity;
import com.benbenlaw.core.block.entity.handler.InputOutputItemHandler;
import com.benbenlaw.resourcefish.util.ResourceType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AquariumBlockEntity extends SyncableBlockEntity {
    public AquariumBlockEntity(BlockPos pos, BlockState state) {
        super(ResourceFishBlockEntities.AQUARIUM_BLOCK_ENTITY.get(), pos, state);
    }

    private ResourceLocation fishTypeId = ResourceType.NONE.getId();

    public ResourceType getFishType() {
        return ResourceType.REGISTRY.getOrDefault(fishTypeId, ResourceType.NONE);
    }

    private int progress;

    public void setFishType(ResourceType fishType) {
        this.fishTypeId = fishType.getId();
        this.progress = 0;
        setChanged();
        sync();
    }

    private final ItemStackHandler inventory = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            sync();
        }

        @Override
        public int getSlotLimit(int slot) {
            return 64;
        };
    };

    private final InputOutputItemHandler buffer = new InputOutputItemHandler(
            inventory, (slot, stack) -> false, (slot) -> true);

    public IItemHandler getItemHandlerCapability(Direction side) {
        return this.buffer;
    }

    public void tick(RandomSource random) {
        ResourceType type = this.getFishType();
        if (type == ResourceType.NONE) return;

        this.progress++;
        if (this.progress < type.getDropIntervalTicks())
            return;

        this.progress = 0;
        for (ItemStack stack : type.rollResults(random)) 
            ItemHandlerHelper.insertItem(inventory, stack, false);  // discard on leftover
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("FishType", fishTypeId.toString());
        tag.putInt("Progress", progress);
        tag.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.fishTypeId = ResourceLocation.parse(tag.getString("FishType"));
        this.progress = tag.getInt("Progress");
        this.inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
    }
}

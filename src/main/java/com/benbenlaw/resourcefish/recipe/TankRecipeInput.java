package com.benbenlaw.resourcefish.recipe;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TankRecipeInput implements RecipeInput {

    private final ItemStackHandler handler;
    private final BlockPos pos;

    public TankRecipeInput(ItemStackHandler handler, BlockPos pos) {
        this.handler = handler;
        this.pos = pos;
    }

    @Override
    public ItemStack getItem(int index) {
        return handler.getStackInSlot(index);
    }

    @Override
    public int size() {
        return handler.getSlots();
    }

    public BlockPos getPos() {
        return pos;
    }
}

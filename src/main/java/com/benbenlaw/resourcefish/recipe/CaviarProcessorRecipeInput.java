package com.benbenlaw.resourcefish.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CaviarProcessorRecipeInput implements RecipeInput {

    private final ItemStackHandler handler;
    private final IFluidHandler fluidHandler;

    public CaviarProcessorRecipeInput(ItemStackHandler handler, IFluidHandler fluidHandler) {
        this.handler = handler;
        this.fluidHandler = fluidHandler;
    }

    @Override
    public ItemStack getItem(int slot) {
        return handler.getStackInSlot(slot);
    }

    @Override
    public int size() {
        return handler.getSlots();
    }

    public IFluidHandler getFluidHandler() {
        return fluidHandler;
    }

    public ItemStackHandler getItemHandler() {
        return handler;
    }
}

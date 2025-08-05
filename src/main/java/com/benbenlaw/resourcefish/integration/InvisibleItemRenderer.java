package com.benbenlaw.resourcefish.integration;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class InvisibleItemRenderer implements IIngredientRenderer<ItemStack> {


    @Override
    public void render(GuiGraphics guiGraphics, ItemStack stack) {

    }

    @Override
    public List<Component> getTooltip(ItemStack stack, TooltipFlag tooltipFlag) {
        return null;
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, ItemStack ingredient, TooltipFlag tooltipFlag) {
    }
}

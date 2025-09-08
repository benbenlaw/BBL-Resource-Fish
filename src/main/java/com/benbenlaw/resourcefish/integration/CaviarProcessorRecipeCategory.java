package com.benbenlaw.resourcefish.integration;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.integration.fish.FishDropsRecipe;
import com.benbenlaw.resourcefish.recipe.CaviarProcessorRecipe;
import com.benbenlaw.resourcefish.recipe.FishInfusingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IScrollGridWidgetFactory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CaviarProcessorRecipeCategory implements IRecipeCategory<CaviarProcessorRecipe> {

    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar_processor");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "textures/gui/jei_fish_drops.png");
    static final RecipeType<CaviarProcessorRecipe> RECIPE_TYPE = RecipeType.create(ResourceFish.MOD_ID, "caviar_processor",
            CaviarProcessorRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;
    private final IScrollGridWidgetFactory<?> scrollGridWidgetFactory;

    private List<ResourceFishEntity> cachedEntity = new ArrayList<>();

    public CaviarProcessorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 140, 37);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ResourceFishBlocks.CAVIAR_PROCESSOR.get()));

        for (int i = 0; i < 3; i++) {
            cachedEntity.add(null);
        }

        this.scrollGridWidgetFactory = helper.createScrollGridFactory(4, 2);
        this.scrollGridWidgetFactory.setPosition(50, 0);
    }

    @Override
    public @Nullable ResourceLocation getRegistryName(CaviarProcessorRecipe recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(CaviarProcessorRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public RecipeType<CaviarProcessorRecipe> getRecipeType() {
        return JEIResourceFishPlugin.PROCESSOR_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe.resourcefish.caviar_processor");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CaviarProcessorRecipe recipe, IFocusGroup iFocusGroup) {

        builder.addSlot(RecipeIngredientRole.INPUT, 7, 11).addItemStack(recipe.caviar());

        for (var result : recipe.getRollResults()) {
            builder.addSlotToWidget(RecipeIngredientRole.OUTPUT, this.scrollGridWidgetFactory)
                    .addItemStack(result.stack())
                    .addRichTooltipCallback((slotView, tooltip) -> {
                        double baseChance = result.chance();
                        int asPercent = Math.round((float) (baseChance * 100));

                        tooltip.add(Component.translatable("jei.resourcefish.chance")
                                .append(String.valueOf(asPercent))
                                .append("%").withStyle(ChatFormatting.GOLD));
                    });
        }

        builder.addSlotToWidget(RecipeIngredientRole.OUTPUT, this.scrollGridWidgetFactory)
                .addFluidStack(recipe.fluidStack().getFluid())
                .addRichTooltipCallback((slotView, tooltip) -> {
                    tooltip.add(Component.translatable("jei.resourcefish.fluid_amount")
                            .append(String.valueOf(recipe.fluidStack().getAmount()))
                            .append("mB").withStyle(ChatFormatting.GOLD));
                    tooltip.add(Component.translatable("jei.resourcefish.needs_tank_upgrade")
                            .withStyle(ChatFormatting.GOLD));
                });

    }

    @Override
    public void draw(CaviarProcessorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
    }
}

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

    private List<ResourceFishEntity> cachedEntity = new ArrayList<>();

    public CaviarProcessorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 140, 37);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ResourceFishBlocks.CAVIAR_PROCESSOR.get()));

        for (int i = 0; i < 3; i++) {
            cachedEntity.add(null);
        }
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

        builder.addSlot(RecipeIngredientRole.INPUT,7, 11).addItemStack(recipe.caviar());

        List<ChanceResult> modifiedOutputs = new ArrayList<>(recipe.results());

        int size = modifiedOutputs.size();
        int centerX = size > 0 ? 1 : 10;
        int centerY = size > 5 ? 2 : 11;
        int xOffset = 0;
        int yOffset = 0;
        int index = 0;

        for (int i = 0; i < size; i++) {
            xOffset = centerX + (i % 5) * 18;
            yOffset = centerY + ((i / 5) * 18);
            index = i;

            int finalIndex = index;
            builder.addSlot(RecipeIngredientRole.OUTPUT, 49 + xOffset, yOffset)
                    .addItemStack(modifiedOutputs.get(i).stack()).addRichTooltipCallback((slotView, tooltip) -> {
                        ChanceResult output = modifiedOutputs.get(finalIndex);
                        float chance = output.chance();
                        tooltip.add(Component.translatable("jei.resourcefish.chance")
                                .append(String.valueOf((int) (chance * 100)))
                                .append("%").withStyle(ChatFormatting.GOLD));
                    }).setBackground(JEIResourceFishPlugin.slotDrawable, -1, -1);
        }
    }

    @Override
    public void draw(CaviarProcessorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
    }
}

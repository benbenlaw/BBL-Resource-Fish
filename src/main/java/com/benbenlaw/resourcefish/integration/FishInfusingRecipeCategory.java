package com.benbenlaw.resourcefish.integration;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.integration.fish.FishIngredient;
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
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FishInfusingRecipeCategory implements IRecipeCategory<FishInfusingRecipe> {

    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "fish_infusing");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "textures/gui/jei_fish_infusing.png");
    static final RecipeType<FishInfusingRecipe> RECIPE_TYPE = RecipeType.create(ResourceFish.MOD_ID, "fish_infusing",
            FishInfusingRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    private List<ResourceFishEntity> cachedEntity = new ArrayList<>();

    @Override
    public @Nullable ResourceLocation getRegistryName(FishInfusingRecipe recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(FishInfusingRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }
    public FishInfusingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 140, 37);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ResourceFishBlocks.TANK_CONTROLLER.get()));

        for (int i = 0; i < 3; i++) {
            cachedEntity.add(null);
        }
    }

    @Override
    public RecipeType<FishInfusingRecipe> getRecipeType() {
        return JEIResourceFishPlugin.INFUSING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.resourcefish.tank_controller");
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
    public void setRecipe(IRecipeLayoutBuilder builder, FishInfusingRecipe recipe, IFocusGroup iFocusGroup) {

        FishIngredient requireFishIngredient = new FishIngredient(recipe.fish(), ResourceFishEntities.RESOURCE_FISH.get());
        FishIngredient createdFishIngredient = new FishIngredient(recipe.createdFish(), ResourceFishEntities.RESOURCE_FISH.get());

        builder.addSlot(RecipeIngredientRole.INPUT,7, 17).addIngredient(JEIResourceFishPlugin.FISH_INGREDIENT_TYPE,  requireFishIngredient);

        addSizedIngredient(builder, RecipeIngredientRole.INPUT, 45, 17, recipe.input1());
        addSizedIngredient(builder, RecipeIngredientRole.INPUT, 63, 17, recipe.input2());
        addSizedIngredient(builder, RecipeIngredientRole.INPUT, 81, 17, recipe.input3());

        builder.addSlot(RecipeIngredientRole.OUTPUT,118, 17).addIngredient(JEIResourceFishPlugin.FISH_INGREDIENT_TYPE,  createdFishIngredient)
                .addRichTooltipCallback(((iRecipeSlotView, iTooltipBuilder) ->
                iTooltipBuilder.add(Component.literal("Chance: ")
                        .append(String.valueOf(100 * recipe.chance()))
                        .append("%")
                        .withStyle(ChatFormatting.GOLD))));
    }

    @Override
    public void draw(FishInfusingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        guiGraphics.drawString(font, Component.literal("Fish inside Tank Area"),1 ,1, 1, false);
    }

    private void addSizedIngredient(IRecipeLayoutBuilder builder, RecipeIngredientRole role, int x, int y, SizedIngredient sizedIngredient) {
        int count = sizedIngredient.count();

        List<ItemStack> stacks = Arrays.stream(sizedIngredient.ingredient().getItems())
                .map(stack -> {
                    ItemStack copy = stack.copy();
                    copy.setCount(count); // set correct count
                    return copy;
                })
                .toList();

        builder.addSlot(role, x, y).addItemStacks(stacks); // one JEI slot, multiple variants
    }
}

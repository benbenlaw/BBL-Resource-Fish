package com.benbenlaw.resourcefish.integration;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.integration.fish.FishIngredient;
import com.benbenlaw.resourcefish.integration.fish.FishIngredientHelper;
import com.benbenlaw.resourcefish.integration.fish.FishIngredientRenderer;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.recipe.FishBreedingRecipe;
import com.benbenlaw.resourcefish.recipe.FishInfusingRecipe;
import com.benbenlaw.resourcefish.recipe.ResourceFishRecipes;
import com.benbenlaw.resourcefish.util.ResourceType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class JEIResourceFishPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "jei_plugin");
    }

    public static IDrawableStatic slotDrawable;
    public static final IIngredientType<FishIngredient> FISH_INGREDIENT_TYPE = () -> FishIngredient.class;

    public static RecipeType<FishBreedingRecipe> BREEDING_RECIPE_TYPE =
            new RecipeType<>(FishBreedingRecipeCategory.UID, FishBreedingRecipe.class);
    public static RecipeType<FishInfusingRecipe> INFUSING_RECIPE_TYPE =
            new RecipeType<>(FishInfusingRecipeCategory.UID, FishInfusingRecipe.class);

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ResourceFishBlocks.TANK_CONTROLLER.get()), FishBreedingRecipeCategory.RECIPE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ResourceFishBlocks.TANK_CONTROLLER.get()), FishInfusingRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new
                FishBreedingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new
                FishInfusingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        slotDrawable = guiHelper.getSlotDrawable();
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        final var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(FishBreedingRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ResourceFishRecipes.FISH_BREEDING_TYPE.get()).stream().map(RecipeHolder::value).toList());

        registration.addRecipes(FishInfusingRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ResourceFishRecipes.FISH_INFUSING_TYPE.get()).stream().map(RecipeHolder::value).toList());

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
       registration.registerSubtypeInterpreter(ResourceFishItems.RESOURCE_FISH_SPAWN_EGG.asItem(), new ItemSubtypeInterpreter());
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {

        List<FishIngredient> fishIngredients = new ArrayList<>();
        for (ResourceType type : ResourceType.getAll()) {
            fishIngredients.add(new FishIngredient(type.getId(), ResourceFishEntities.RESOURCE_FISH.get()));
        }
        registration.register(FISH_INGREDIENT_TYPE, fishIngredients,
                new FishIngredientHelper(), new FishIngredientRenderer());
    }
}

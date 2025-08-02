package com.benbenlaw.resourcefish.integration;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.recipe.FishBreedingRecipe;
import com.benbenlaw.resourcefish.recipe.ResourceFishRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;

@JeiPlugin
public class JEIResourceFishPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "jei_plugin");
    }

    public static IDrawableStatic slotDrawable;


    public static RecipeType<FishBreedingRecipe> BREEDING_RECIPE_TYPE =
            new RecipeType<>(FishBreedingRecipeCategory.UID, FishBreedingRecipe.class);

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ResourceFishBlocks.TANK_CONTROLLER.get()), FishBreedingRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {

        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new
                FishBreedingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        slotDrawable = guiHelper.getSlotDrawable();
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert Minecraft.getInstance().level != null;
        final var recipeManager = Minecraft.getInstance().level.getRecipeManager();

        registration.addRecipes(FishBreedingRecipeCategory.RECIPE_TYPE,
                recipeManager.getAllRecipesFor(ResourceFishRecipes.FISH_BREEDING_TYPE.get()).stream().map(RecipeHolder::value).toList());

    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
       registration.registerSubtypeInterpreter(ResourceFishItems.RESOURCE_FISH_SPAWN_EGG.asItem(), new ItemSubtypeInterpreter());
    }
}

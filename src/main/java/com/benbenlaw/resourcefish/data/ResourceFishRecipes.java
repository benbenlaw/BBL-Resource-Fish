package com.benbenlaw.resourcefish.data;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.data.builders.FishBreedingRecipeBuilder;
import com.benbenlaw.resourcefish.data.builders.FishInfusingRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.concurrent.CompletableFuture;

public class ResourceFishRecipes extends RecipeProvider {

    public ResourceFishRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {

        //Breeding Stone Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("water"), createFish("lava"),
                SizedIngredient.of(Items.STONE, 64),100, 1.0, createFish("stone"))
                .unlockedBy("has_item", has(Items.STONE)).save(consumer);

        //Breeding Stone Coal Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("stone"), createFish("wood"),
                SizedIngredient.of(Items.CHARCOAL, 64), 100, 1.0, createFish("coal"))
                .unlockedBy("has_item", has(Items.CHARCOAL)).save(consumer);

        //Breeding Diamond Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("coal"), createFish("coal"),
                SizedIngredient.of(Items.DIAMOND, 4), 100, 0.15, createFish("diamond"))
                .unlockedBy("has_item", has(Items.DIAMOND)).save(consumer);

        //Breeding Emerald Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("lapis"), createFish("lapis"),
                SizedIngredient.of(Items.EMERALD, 4), 100, 0.15, createFish("emerald"))
                .unlockedBy("has_item", has(Items.EMERALD)).save(consumer);

        //Infusing Copper Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("coal"),
                SizedIngredient.of(Items.COPPER_INGOT, 12),
                SizedIngredient.of(Items.COPPER_INGOT, 12),
                SizedIngredient.of(Items.COPPER_INGOT, 12),
                100, 1.0, createFish("copper"))
                .unlockedBy("has_item", has(Items.COPPER_INGOT)).save(consumer);

        //Infusing Iron Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("copper"),
                SizedIngredient.of(Items.IRON_INGOT, 12),
                SizedIngredient.of(Items.IRON_INGOT, 12),
                SizedIngredient.of(Items.IRON_INGOT, 12),
                100, 1.0, createFish("iron"))
                .unlockedBy("has_item", has(Items.IRON_INGOT)).save(consumer);

        //Infusing Lapis Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("copper"),
                SizedIngredient.of(Items.LAPIS_LAZULI, 12),
                SizedIngredient.of(Items.LAPIS_LAZULI, 12),
                SizedIngredient.of(Items.LAPIS_LAZULI, 12),
                100, 1.0, createFish("lapis"))
                .unlockedBy("has_item", has(Items.LAPIS_LAZULI)).save(consumer);

        //Infusing Redstone Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("iron"),
                SizedIngredient.of(Items.REDSTONE, 12),
                SizedIngredient.of(Items.REDSTONE, 12),
                SizedIngredient.of(Items.REDSTONE, 12),
                100, 1.0, createFish("redstone"))
                .unlockedBy("has_item", has(Items.REDSTONE)).save(consumer);

        //Infusing Gold Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("redstone"),
                SizedIngredient.of(Items.GOLD_INGOT, 12),
                SizedIngredient.of(Items.GOLD_INGOT, 12),
                SizedIngredient.of(Items.GOLD_INGOT, 12),
                100, 1.0, createFish("gold"))
                .unlockedBy("has_item", has(Items.GOLD_INGOT)).save(consumer);

        //Infusing Granite Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("stone"),
                SizedIngredient.of(Items.GRANITE, 12),
                SizedIngredient.of(Items.GRANITE, 12),
                SizedIngredient.of(Items.GRANITE, 12),
                100, 1.0, createFish("granite"))
                .unlockedBy("has_item", has(Items.GRANITE)).save(consumer);

        //Infusing Diorite Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("stone"),
                SizedIngredient.of(Items.DIORITE, 12),
                SizedIngredient.of(Items.DIORITE, 12),
                SizedIngredient.of(Items.DIORITE, 12),
                100, 1.0, createFish("diorite"))
                .unlockedBy("has_item", has(Items.DIORITE)).save(consumer);

        //Infusing Andesite Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("stone"),
                SizedIngredient.of(Items.ANDESITE, 12),
                SizedIngredient.of(Items.ANDESITE, 12),
                SizedIngredient.of(Items.ANDESITE, 12),
                100, 1.0, createFish("andesite"))
                .unlockedBy("has_item", has(Items.ANDESITE)).save(consumer);

    }

    public static ResourceLocation createFish(String resource) {
        return ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, resource);
    }

}

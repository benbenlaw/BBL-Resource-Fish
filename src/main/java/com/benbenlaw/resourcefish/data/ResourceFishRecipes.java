package com.benbenlaw.resourcefish.data;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.data.builders.CaviarProcessorRecipeBuilder;
import com.benbenlaw.resourcefish.data.builders.FishBreedingRecipeBuilder;
import com.benbenlaw.resourcefish.data.builders.FishInfusingRecipeBuilder;
import com.benbenlaw.resourcefish.item.CaviarItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import java.util.List;
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


        //Caviar
        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("granite"),
                List.of(new ChanceResult(new ItemStack(Items.GRANITE), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/granite"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("diorite"),
                List.of(new ChanceResult(new ItemStack(Items.DIORITE), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/diorite"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("andesite"),
                List.of(new ChanceResult(new ItemStack(Items.ANDESITE), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/andesite"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("emerald"),
                List.of(new ChanceResult(new ItemStack(Items.EMERALD), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/emerald"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("diamond"),
                List.of(new ChanceResult(new ItemStack(Items.DIAMOND), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/diamond"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("gold"),
                List.of(new ChanceResult(new ItemStack(Items.GOLD_INGOT), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/gold"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("redstone"),
                List.of(new ChanceResult(new ItemStack(Items.REDSTONE), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/redstone"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("lapis"),
                List.of(new ChanceResult(new ItemStack(Items.LAPIS_LAZULI), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/lapis"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("iron"),
                List.of(new ChanceResult(new ItemStack(Items.IRON_INGOT), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/iron"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("copper"),
                List.of(new ChanceResult(new ItemStack(Items.COPPER_INGOT), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/copper"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("coal"),
                List.of(new ChanceResult(new ItemStack(Items.COAL), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/coal"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("coal"),
                List.of(
                        new ChanceResult(new ItemStack(Items.OAK_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.SPRUCE_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.BIRCH_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.JUNGLE_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.ACACIA_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.DARK_OAK_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.CHERRY_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.MANGROVE_LOG), 0.05f)
                ));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("cobblestone"),
                List.of(new ChanceResult(new ItemStack(Items.COBBLESTONE), 0.4f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/cobblestone"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("stone"),
                List.of(new ChanceResult(new ItemStack(Items.STONE), 0.4f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/stone"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("dirt"),
                List.of(new ChanceResult(new ItemStack(Items.DIRT), 0.4f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/dirt"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("skeleton"),
                List.of(
                        new ChanceResult(new ItemStack(Items.BONE), 0.2f),
                        new ChanceResult(new ItemStack(Items.SKELETON_SKULL), 0.01f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/skeleton"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("zombie"),
                List.of(
                        new ChanceResult(new ItemStack(Items.ROTTEN_FLESH), 0.2f),
                        new ChanceResult(new ItemStack(Items.POTATO), 0.05f),
                        new ChanceResult(new ItemStack(Items.CARROT), 0.05f),
                        new ChanceResult(new ItemStack(Items.ZOMBIE_HEAD), 0.01f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/zombie"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("creeper"),
                List.of(
                        new ChanceResult(new ItemStack(Items.GUNPOWDER), 0.2f),
                        new ChanceResult(new ItemStack(Items.CREEPER_HEAD), 0.01f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/creeper"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("spider"),
                List.of(
                        new ChanceResult(new ItemStack(Items.STRING), 0.2f),
                        new ChanceResult(new ItemStack(Items.SPIDER_EYE), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/spider"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("ender"),
                List.of(new ChanceResult(new ItemStack(Items.ENDER_PEARL), 0.2f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/ender"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("wood"),
                List.of(
                        new ChanceResult(new ItemStack(Items.OAK_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.SPRUCE_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.BIRCH_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.JUNGLE_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.ACACIA_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.DARK_OAK_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.CHERRY_LOG), 0.05f),
                        new ChanceResult(new ItemStack(Items.MANGROVE_LOG), 0.05f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/wood"));







    }

    public static ResourceLocation createFish(String resource) {
        return ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, resource);
    }

}

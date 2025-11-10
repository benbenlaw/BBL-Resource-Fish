package com.benbenlaw.resourcefish.data;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.data.builders.CaviarProcessorRecipeBuilder;
import com.benbenlaw.resourcefish.data.builders.FishBreedingRecipeBuilder;
import com.benbenlaw.resourcefish.data.builders.FishInfusingRecipeBuilder;
import com.benbenlaw.resourcefish.item.CaviarItem;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.util.ResourceFishTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;

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
                SizedIngredient.of(Items.COAL, 64), 100, 1.0, createFish("coal"))
                .unlockedBy("has_item", has(Items.COAL)).save(consumer);

        //Breeding Diamond Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("coal"), createFish("coal"),
                SizedIngredient.of(Items.DIAMOND, 4), 100, 0.15, createFish("diamond"))
                .unlockedBy("has_item", has(Items.DIAMOND)).save(consumer);

        //Breeding Emerald Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("lapis"), createFish("lapis"),
                SizedIngredient.of(Items.EMERALD, 4), 100, 0.15, createFish("emerald"))
                .unlockedBy("has_item", has(Items.EMERALD)).save(consumer);

        //Infusing Sand Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("water"),
                SizedIngredient.of(Items.SAND, 64),
                SizedIngredient.of(Items.SAND, 64),
                SizedIngredient.of(Items.SAND, 64),
                100, 1.0, createFish("sand"))
                .unlockedBy("has_item", has(Items.SAND)).save(consumer);

        //Infusing Gravel Fish
        FishInfusingRecipeBuilder.createFishInfusingRecipe(createFish("stone"),
                SizedIngredient.of(Items.GRAVEL, 64),
                SizedIngredient.of(Items.GRAVEL, 64),
                SizedIngredient.of(Items.GRAVEL, 64),
                100, 1.0, createFish("gravel"))
                .unlockedBy("has_item", has(Items.GRAVEL)).save(consumer);

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

        //Breeding Netherrack Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("lava"), createFish("lava"),
                        SizedIngredient.of(Items.STONE, 64), 100, 0.6, createFish("netherrack"))
                .unlockedBy("has_item", has(Items.STONE)).save(consumer);

        //Breeding Soul Sand Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("netherrack"), createFish("sand"),
                        SizedIngredient.of(Items.SAND, 64), 100, 0.6, createFish("soul_sand"))
                .unlockedBy("has_item", has(Items.SOUL_SAND)).save(consumer);

        //Breeding Water Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("water"), createFish("water"),
                        SizedIngredient.of(Items.WATER_BUCKET, 1), 100, 0.75, createFish("water"))
                .unlockedBy("has_item", has(Items.WATER_BUCKET)).save(consumer);

        //Breeding Lava Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("lava"), createFish("lava"),
                        SizedIngredient.of(Items.LAVA_BUCKET, 1), 100, 0.75, createFish("lava"))
                .unlockedBy("has_item", has(Items.LAVA_BUCKET)).save(consumer);

        //Breeding Wood Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("wood"), createFish("wood"),
                        SizedIngredient.of(ItemTags.LOGS, 64), 100, 0.75, createFish("wood"))
                .unlockedBy("has_item", has(ItemTags.LOGS)).save(consumer);

        //Breeding Dirt Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("dirt"), createFish("dirt"),
                        SizedIngredient.of(Items.DIRT, 64), 100, 0.75, createFish("dirt"))
                .unlockedBy("has_item", has(Items.DIRT)).save(consumer);

        //Breeding Cobblestone Fish
        FishBreedingRecipeBuilder.createFishBreedingRecipe(createFish("stone"), createFish("stone"),
                        SizedIngredient.of(Items.COBBLESTONE, 64), 100, 0.75, createFish("cobblestone"))
                .unlockedBy("has_item", has(Items.COBBLESTONE)).save(consumer);

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

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("water"),
                List.of()).withFluid(new FluidStack(Fluids.WATER, 10))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/water"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("lava"),
                List.of()).withFluid(new FluidStack(Fluids.LAVA, 10))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/lava"));

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

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("netherrack"),
                List.of(new ChanceResult(new ItemStack(Items.NETHERRACK), 0.1f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/netherrack"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("soul_sand"),
                List.of(new ChanceResult(new ItemStack(Items.SOUL_SAND), 0.1f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/soul_sand"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("sand"),
                List.of(
                        new ChanceResult(new ItemStack(Items.SAND), 0.2f),
                        new ChanceResult(new ItemStack(Items.RED_SAND), 0.1f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/sand"));

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("gravel"),
                        List.of(
                        new ChanceResult(new ItemStack(Items.GRAVEL), 0.2f),
                        new ChanceResult(new ItemStack(Items.FLINT), 0.05f)))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/gravel"));

        // ***** Crafting Recipes *****
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishBlocks.TANK_CONTROLLER)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', ResourceFishItems.RESOURCE_FISH_BUCKET)
                .unlockedBy("has_item", has(ResourceFishItems.RESOURCE_FISH_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishBlocks.CAVIAR_PROCESSOR)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.DEPTH_UPGRADE_1)
                .pattern("AAA")
                .pattern(" B ")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.DEPTH_UPGRADE_2)
                .pattern("AAA")
                .pattern(" B ")
                .pattern("AAA")
                .define('A', Items.GOLD_INGOT)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.DEPTH_UPGRADE_3)
                .pattern("AAA")
                .pattern(" B ")
                .pattern("AAA")
                .define('A', Items.DIAMOND)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.WIDTH_UPGRADE_1)
                .pattern("A A")
                .pattern("ABA")
                .pattern("A A")
                .define('A', Items.IRON_INGOT)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.WIDTH_UPGRADE_2)
                .pattern("A A")
                .pattern("ABA")
                .pattern("A A")
                .define('A', Items.GOLD_INGOT)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.WIDTH_UPGRADE_3)
                .pattern("A A")
                .pattern("ABA")
                .pattern("A A")
                .define('A', Items.DIAMOND)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.SPEED_UPGRADE_1)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.SUGAR)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.SPEED_UPGRADE_2)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.GLOWSTONE_DUST)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.SPEED_UPGRADE_3)
                .pattern(" A ")
                .pattern("ABA")
                .pattern(" A ")
                .define('A', Items.END_ROD)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.TANK_UPGRADE)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', Tags.Items.GLASS_BLOCKS)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishItems.RESOURCE_FISH_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.BREEDING_UPGRADE)
                .pattern("AAA")
                .pattern("B B")
                .pattern("AAA")
                .define('A', Items.IRON_INGOT)
                .define('B', ResourceFishItems.RESOURCE_FISH_BUCKET)
                .unlockedBy("has_item", has(ResourceFishItems.RESOURCE_FISH_BUCKET))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.INFUSING_UPGRADE)
                .pattern("AAA")
                .pattern("BCB")
                .pattern("AAA")
                .define('A', Items.BONE_MEAL)
                .define('B', ResourceFishItems.RESOURCE_FISH_BUCKET)
                .define('C', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishItems.INFUSING_UPGRADE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ResourceFishItems.ROUND_ROBIN_UPGRADE)
                .pattern("BAB")
                .pattern("ABA")
                .pattern("BAB")
                .define('A', Items.REDSTONE)
                .define('B', ResourceFishTags.Items.CAVIAR)
                .unlockedBy("has_item", has(ResourceFishTags.Items.CAVIAR))
                .save(consumer);
















    }

    public static ResourceLocation createFish(String resource) {
        return ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, resource);
    }

}

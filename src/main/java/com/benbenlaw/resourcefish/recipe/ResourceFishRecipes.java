package com.benbenlaw.resourcefish.recipe;

import com.benbenlaw.resourcefish.ResourceFish;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ResourceFishRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ResourceFish.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, ResourceFish.MOD_ID);

    //Breeding Recipe
    public static final Supplier<RecipeSerializer<FishBreedingRecipe>> FISH_BREEDING_SERIALIZER =
            SERIALIZER.register("fish_breeding", () -> FishBreedingRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeType<FishBreedingRecipe>> FISH_BREEDING_TYPE =
            TYPES.register("fish_breeding", () -> FishBreedingRecipe.Type.INSTANCE);

    //Infusing Recipe
    public static final Supplier<RecipeSerializer<FishInfusingRecipe>> FISH_INFUSING_SERIALIZER =
            SERIALIZER.register("fish_infusing", () -> FishInfusingRecipe.Serializer.INSTANCE);

    public static final Supplier<RecipeType<FishInfusingRecipe>> FISH_INFUSING_TYPE =
            TYPES.register("fish_infusing", () -> FishInfusingRecipe.Type.INSTANCE);

}

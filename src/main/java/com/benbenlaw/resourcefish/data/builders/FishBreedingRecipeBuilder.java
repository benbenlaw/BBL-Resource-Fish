package com.benbenlaw.resourcefish.data.builders;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.recipe.FishBreedingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class FishBreedingRecipeBuilder implements RecipeBuilder {

    protected String group;
    protected ResourceLocation parentIngredientA;
    protected ResourceLocation parentIngredientB;
    protected SizedIngredient breedingIngredient;
    protected int duration;
    protected double chance;
    protected ResourceLocation createdFish;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public FishBreedingRecipeBuilder(ResourceLocation parentIngredientA, ResourceLocation parentIngredientB,
                             SizedIngredient breedingIngredient, int duration, double chance,
                             ResourceLocation createdFish) {
        this.parentIngredientA = parentIngredientA;
        this.parentIngredientB = parentIngredientB;
        this.breedingIngredient = breedingIngredient;
        this.duration = duration;
        this.chance = chance;
        this.createdFish = createdFish;
    }

    public static FishBreedingRecipeBuilder createFishBreedingRecipe(ResourceLocation parentIngredientA, ResourceLocation parentIngredientB,
            SizedIngredient breedingIngredient, int duration, double chance, ResourceLocation createdFish) {
        return new FishBreedingRecipeBuilder(parentIngredientA, parentIngredientB, breedingIngredient, duration, chance, createdFish);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String goupName) {
        this.group = goupName;
        return this;
    }

    @Override
    public Item getResult() {
        return ItemStack.EMPTY.getItem();
    }

    public void save(@NotNull RecipeOutput recipeOutput) {
        this.save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "breeding/" +
                createdFish.getPath()));
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        Advancement.Builder builder = Advancement.Builder.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        FishBreedingRecipe fishBreedingRecipe = new FishBreedingRecipe(
                parentIngredientA, parentIngredientB, breedingIngredient, duration, chance, createdFish);
        recipeOutput.accept(id, fishBreedingRecipe, builder.build(id.withPrefix("recipes/breeding/")));

    }
}

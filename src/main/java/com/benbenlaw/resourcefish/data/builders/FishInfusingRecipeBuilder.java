package com.benbenlaw.resourcefish.data.builders;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.recipe.FishBreedingRecipe;
import com.benbenlaw.resourcefish.recipe.FishInfusingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
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

public class FishInfusingRecipeBuilder implements RecipeBuilder {

    protected String group;
    protected ResourceLocation fish;
    protected SizedIngredient input1;
    protected SizedIngredient input2;
    protected SizedIngredient input3;
    protected int duration;
    protected double chance;
    protected ResourceLocation createdFish;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public FishInfusingRecipeBuilder(ResourceLocation fish, SizedIngredient input1, SizedIngredient input2, SizedIngredient input3,
                                     int duration, double chance, ResourceLocation createdFish) {
        this.fish = fish;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.duration = duration;
        this.chance = chance;
        this.createdFish = createdFish;
    }

    public static FishInfusingRecipeBuilder createFishInfusingRecipe(ResourceLocation fish, SizedIngredient input1, SizedIngredient input2,
                                                                      SizedIngredient input3, int duration,
                                                                      double chance, ResourceLocation createdFish) {
        return new FishInfusingRecipeBuilder(fish, input1, input2, input3, duration, chance, createdFish);
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
        this.save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "infusing/" +
                createdFish.getPath()));
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        Advancement.Builder builder = Advancement.Builder.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        FishInfusingRecipe fishInfusingRecipe = new FishInfusingRecipe(
                fish, input1, input2, input3, duration, chance, createdFish);
        recipeOutput.accept(id, fishInfusingRecipe, builder.build(id.withPrefix("recipes/infusing/")));

    }
}

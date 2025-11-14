package com.benbenlaw.resourcefish.data.builders;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.recipe.CaviarProcessorRecipe;
import com.benbenlaw.resourcefish.recipe.FishBreedingRecipe;
import com.benbenlaw.resourcefish.screen.CaviarProcessorScreen;
import com.benbenlaw.resourcefish.util.SizedIngredientChanceResult;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CaviarProcessorRecipeBuilder implements RecipeBuilder {

    protected String group;
    protected ItemStack caviar;
    protected List<SizedIngredientChanceResult> results;
    protected FluidStack fluidStack;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public CaviarProcessorRecipeBuilder(ItemStack caviar, List<SizedIngredientChanceResult> results) {
        this.caviar = caviar;
        this.results = results;
        this.fluidStack = FluidStack.EMPTY;
    }

    public static CaviarProcessorRecipeBuilder caviarProcessorRecipeBuilder(ItemStack caviar, List<SizedIngredientChanceResult> results) {
        return new CaviarProcessorRecipeBuilder(caviar, results);
    }

    public CaviarProcessorRecipeBuilder withFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
        return this;
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
        this.save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/"));
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull String name) {
        this.save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "caviar/" + name));
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        Advancement.Builder builder = Advancement.Builder.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        NonNullList<SizedIngredientChanceResult> emptyNonNullList = NonNullList.create();
        emptyNonNullList.addAll(results);
        CaviarProcessorRecipe caviarProcessorRecipe = new CaviarProcessorRecipe(caviar, emptyNonNullList, fluidStack);
        recipeOutput.accept(id, caviarProcessorRecipe, builder.build(id.withPrefix("recipes/caviar/")));

    }
}

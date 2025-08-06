package com.benbenlaw.resourcefish.integration.fish;

import com.benbenlaw.core.recipe.ChanceResult;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record FishDropsRecipe(ResourceLocation id, List<String> biomes, int dropInterval, List<ChanceResult> drops, FishIngredient fish ) {

}

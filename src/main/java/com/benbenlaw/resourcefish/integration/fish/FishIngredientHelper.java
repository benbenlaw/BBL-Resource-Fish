package com.benbenlaw.resourcefish.integration.fish;

import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.integration.JEIResourceFishPlugin;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class FishIngredientHelper implements IIngredientHelper<FishIngredient> {

    @Override
    public IIngredientType<FishIngredient> getIngredientType() {
        return JEIResourceFishPlugin.FISH_INGREDIENT_TYPE;  // your registered ingredient type
    }

    @Override
    public String getDisplayName(FishIngredient ingredient) {
        // Return a user-friendly name for your fish (for JEI search and tooltip)
        return ingredient.getFishType().toString();
    }

    @Override
    public String getUniqueId(FishIngredient ingredient, UidContext context) {
        // Return a unique ID string for this fish ingredient (used internally by JEI)
        return ingredient.getFishType().toString();
    }

    @Override
    public String getDisplayModId(FishIngredient ingredient) {
        // Return the mod id namespace of your fish ingredient (optional)
        return ingredient.getFishType().getNamespace();
    }

    @Override
    public ResourceLocation getResourceLocation(FishIngredient fishIngredient) {
        return fishIngredient.getFishType();
    }

    @Override
    public FishIngredient copyIngredient(FishIngredient ingredient) {
        // Return a copy of your ingredient to avoid mutation issues
        return new FishIngredient(ingredient.getFishType(), ResourceFishEntities.RESOURCE_FISH.get());
    }

    @Override
    public String getErrorInfo(@Nullable FishIngredient ingredient) {
        if (ingredient == null) {
            return "FishIngredient:null";
        }
        if (ingredient.getFishType() == null) {
            return "FishIngredient:entityType_null";
        }
        return ingredient.getFishType().toString();
    }
}

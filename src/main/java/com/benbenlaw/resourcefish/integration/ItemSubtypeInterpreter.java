package com.benbenlaw.resourcefish.integration;

import com.benbenlaw.resourcefish.item.ResourceFishDataComponents;
import com.benbenlaw.resourcefish.item.ResourceFishSpawnEgg;
import com.benbenlaw.resourcefish.util.ResourceType;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ItemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    @Override
    public @Nullable Object getSubtypeData(ItemStack stack, UidContext uidContext) {
        return stack.getOrDefault(ResourceFishDataComponents.FISH_TYPE, ResourceType.NONE);
    }

    @Override
    public String getLegacyStringSubtypeInfo(ItemStack stack, UidContext uidContext) {
        return stack.getOrDefault(ResourceFishDataComponents.FISH_TYPE, ResourceType.NONE).toString();
    }


}

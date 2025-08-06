package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class CaviarItem extends Item {

    public CaviarItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        ResourceLocation resourceType = stack.get(ResourceFishDataComponents.FISH_TYPE.get());

        if (resourceType != null) {
            String[] parts = resourceType.getPath().split("_");
            for (int i = 0; i < parts.length; i++) {
                if (!parts[i].isEmpty()) {
                    parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
                }
            }
            String formattedName = String.join(" ", parts);

            Component baseName = Component.translatable(this.getDescriptionId());
            return Component.literal(formattedName + " ").append(baseName);
        }

        return super.getName(stack);
    }

    public static ItemStack createCaviarStack(String path) {
        ItemStack stack = new ItemStack(ResourceFishItems.CAVIAR.get());
        stack.set(ResourceFishDataComponents.FISH_TYPE, ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, path));
        return stack;
    }

}

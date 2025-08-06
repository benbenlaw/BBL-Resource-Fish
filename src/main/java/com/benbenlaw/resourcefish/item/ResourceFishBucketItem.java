package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluid;

public class ResourceFishBucketItem extends MobBucketItem {
    public ResourceFishBucketItem(EntityType<? extends ResourceFishEntity> fishType, Fluid fluid, SoundEvent sound, Properties properties) {
        super(fishType, fluid, sound, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        ResourceLocation resourceType = stack.get(ResourceFishDataComponents.FISH_TYPE.get());

        if (resourceType != null) {
            String name = resourceType.getPath();

            String[] parts = name.split("_");
            for (int i = 0; i < parts.length; i++) {
                if (!parts[i].isEmpty()) {
                    parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
                }
            }
            String formattedName = String.join(" ", parts);

            // Use translatable text with formattedName as parameter
            return Component.translatable("item.resourcefish.resource_fish_bucket", formattedName);
        }

        return super.getName(stack);
    }
}

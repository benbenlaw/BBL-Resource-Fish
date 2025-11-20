package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void checkExtraContent(@Nullable Player player, Level level, ItemStack stack, BlockPos pos) {
        super.checkExtraContent(player, level, stack, pos);

        if (!(level instanceof ServerLevel server)) return;

        List<ResourceFishEntity> fishList = server.getEntitiesOfClass(ResourceFishEntity.class,
                new AABB(pos).inflate(1));

        if (fishList.isEmpty()) return;

        ResourceFishEntity fish = fishList.getFirst();

        CustomData custom = stack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
        if (!custom.isEmpty()) {
            fish.loadFromBucketTag(custom.copyTag());
        }

        ResourceLocation resourceType = stack.get(ResourceFishDataComponents.FISH_TYPE.get());
        if (resourceType != null) {
            fish.setResourceType(ResourceType.REGISTRY.get(resourceType));
        }
    }


}

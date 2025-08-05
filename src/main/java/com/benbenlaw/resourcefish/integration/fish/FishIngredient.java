package com.benbenlaw.resourcefish.integration.fish;

import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class FishIngredient {
    private static final Map<FishIngredient, WeakReference<ResourceFishEntity>> cache = new WeakHashMap<>();

    public final ResourceLocation fishType; // e.g. resourcefish:gold_fish
    private final EntityType<ResourceFishEntity> entityType;

    public FishIngredient(ResourceLocation fishType, EntityType<ResourceFishEntity> entityType) {
        this.fishType = fishType;
        this.entityType = entityType;
    }

    public ResourceFishEntity getCachedEntity(Level world) {
        ResourceFishEntity entity = null;
        WeakReference<ResourceFishEntity> ref = cache.get(this);
        if (ref != null) entity = ref.get();
        if (entity == null && world != null) {
            entity = entityType.create(world);
            if (entity != null) {
                entity.setResourceType(ResourceType.get(fishType));
                entity.setVariant(ResourceFishEntity.generateVariant(ResourceType.get(fishType), world.getRandom()));
                cache.put(this, new WeakReference<>(entity));
            }
        }
        return entity;
    }

    public ResourceLocation getFishType() {
        return fishType;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FishIngredient)) return false;
        return fishType.equals(((FishIngredient)o).fishType);
    }

    @Override
    public int hashCode() {
        return fishType.hashCode();
    }
}

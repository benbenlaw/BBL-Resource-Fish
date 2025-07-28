package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.material.Fluid;

public class ResourceFishBucketItem extends MobBucketItem {
    public ResourceFishBucketItem(EntityType<? extends ResourceFishEntity> fishType, Fluid fluid, SoundEvent sound, Properties properties) {
        super(fishType, fluid, sound, properties);
    }


}

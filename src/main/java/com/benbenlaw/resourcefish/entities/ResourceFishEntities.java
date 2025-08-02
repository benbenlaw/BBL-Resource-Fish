package com.benbenlaw.resourcefish.entities;

import com.benbenlaw.resourcefish.ResourceFish;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ResourceFishEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ResourceFish.MOD_ID);


    public static final Supplier<EntityType<ResourceFishEntity>> RESOURCE_FISH =
            ENTITIES.register("resource_fish", () -> EntityType.Builder.of(ResourceFishEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5F, 0.3F)
                    .clientTrackingRange(8)
                    .updateInterval(3)
                    .build(ResourceFish.MOD_ID + ":resource_fish"));

}

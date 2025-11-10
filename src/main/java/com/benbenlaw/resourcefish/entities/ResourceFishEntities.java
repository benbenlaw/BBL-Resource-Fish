package com.benbenlaw.resourcefish.entities;

import com.benbenlaw.resourcefish.ResourceFish;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
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
                    .canSpawnFarFromPlayer()
                    .build(ResourceFish.MOD_ID + ":resource_fish"));


    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(
                ResourceFishEntities.RESOURCE_FISH.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.OCEAN_FLOOR_WG,
                ResourceFishEntity::canSpawnHere, // uses LevelAccessor
                RegisterSpawnPlacementsEvent.Operation.REPLACE
        );
    }

}

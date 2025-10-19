package com.benbenlaw.resourcefish.event;

import com.benbenlaw.core.block.colored.util.IColored;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.ResourceFishDataComponents;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.item.ResourceFishSpawnEgg;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.Map;


@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> {
                    int fallback = 0xAAAAAA;

                    ResourceLocation typeId = stack.get(ResourceFishDataComponents.FISH_TYPE);
                    int mainColor = fallback;
                    int patternColor = fallback;

                    if (typeId != null) {
                        ResourceType type = ResourceType.get(typeId);
                        if (type != null) {
                            mainColor = type.getColor();
                            patternColor = type.getPatternColor();
                        }
                    }

                    // Base
                    if (tintIndex == 0) {
                        //return 0xFF394F89; // Blue-ish color
                        return (0xFF << 24) | (mainColor & 0xFFFFFF);
                    }

                    // Pattern
                    if (tintIndex == 1) {
                        return (0xFF << 24) | (patternColor & 0xFFFFFF);
                    }

                    return 0xFFFFFFFF; // Default for unexpected indexes
                },

                ResourceFishItems.CAVIAR.get(),
                ResourceFishItems.RESOURCE_FISH_SPAWN_EGG.get()
        );
    }
}

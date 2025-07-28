package com.benbenlaw.resourcefish;

import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.renderer.RedfinRenderer;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.apache.logging.log4j.LogManager;

@Mod(ResourceFish.MOD_ID)
public class ResourceFish{

    public static final String MOD_ID = "resourcefish";
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    public ResourceFish(final IEventBus modEventBus, final ModContainer modContainer) {

        ResourceFishItems.ITEMS.register(modEventBus);

        ResourceFishEntities.ENTITIES.register(modEventBus);
        modEventBus.addListener(ResourceFish::registerAttributes);

    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ResourceFishEntities.RESOURCE_FISH.get(), ResourceFishEntity.createAttributes().build());
    }

    @EventBusSubscriber(modid = ResourceFish.MOD_ID, bus = EventBusSubscriber.Bus.MOD ,value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ResourceFishEntities.RESOURCE_FISH.get(), RedfinRenderer::new);
        }

    }


}

package com.benbenlaw.resourcefish;

import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.block.entity.ResourceFishBlockEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.event.ClientEvents;
import com.benbenlaw.resourcefish.item.ResourceFishCreativeTab;
import com.benbenlaw.resourcefish.item.ResourceFishDataComponents;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.recipe.ResourceFishRecipes;
import com.benbenlaw.resourcefish.renderer.ResourceFishRenderer;
import com.benbenlaw.resourcefish.screen.ResourceFishMenuTypes;
import com.benbenlaw.resourcefish.screen.TankControllerScreen;
import com.benbenlaw.resourcefish.util.ResourceTypeLoader;
import com.google.gson.Gson;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.apache.logging.log4j.LogManager;

@Mod(ResourceFish.MOD_ID)
public class ResourceFish{

    public static final String MOD_ID = "resourcefish";
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    public ResourceFish(final IEventBus modEventBus, final ModContainer modContainer) {

        ResourceFishCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        ResourceFishBlocks.BLOCKS.register(modEventBus);
        ResourceFishBlockEntities.BLOCK_ENTITIES.register(modEventBus);

        ResourceFishItems.ITEMS.register(modEventBus);
        ResourceFishDataComponents.COMPONENTS.register(modEventBus);
        ResourceFishEntities.ENTITIES.register(modEventBus);

        ResourceFishMenuTypes.MENUS.register(modEventBus);

        ResourceFishRecipes.TYPES.register(modEventBus);
        ResourceFishRecipes.SERIALIZER.register(modEventBus);

        modEventBus.addListener(ResourceFish::registerAttributes);
        modEventBus.addListener(ResourceFish::registerCapabilities);

        NeoForge.EVENT_BUS.addListener(ResourceFish::onAddReloadListener);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.register(new ClientEvents());
        }

    }
    @SubscribeEvent
    private static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new ResourceTypeLoader(new Gson(), "resource_types"));
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ResourceFishEntities.RESOURCE_FISH.get(), ResourceFishEntity.createAttributes().build());
    }

    @EventBusSubscriber(modid = ResourceFish.MOD_ID, bus = EventBusSubscriber.Bus.MOD ,value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ResourceFishEntities.RESOURCE_FISH.get(), ResourceFishRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ResourceFishMenuTypes.TANK_CONTROLLER_MENU.get(), TankControllerScreen::new);
        }
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        ResourceFishBlockEntities.registerCapabilities(event);
    }

}

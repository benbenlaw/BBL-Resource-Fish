package com.benbenlaw.resourcefish.network;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.network.packets.SyncResourceTypesToClient;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ResourceFishNetworking {
    public static void registerNetworking(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ResourceFish.MOD_ID);

        //To Client From Server
        registrar.playToClient(SyncResourceTypesToClient.TYPE, SyncResourceTypesToClient.STREAM_CODEC, SyncResourceTypesToClient.HANDLER);


    }

}

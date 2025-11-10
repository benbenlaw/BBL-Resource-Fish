package com.benbenlaw.resourcefish.event;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.network.ResourceFishNetworking;
import com.benbenlaw.resourcefish.network.packets.SyncResourceTypesToClient;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ResourceFish.MOD_ID)
public class LoginEvent {

    @SubscribeEvent
    public static void onDataPackSync(OnDatapackSyncEvent event) {

        if (event.getPlayer() == null) {
            for (ServerPlayer player : event.getPlayerList().getPlayers()) {
                ResourceType.sendResourceTypes(player);
            }
        } else {
            ResourceType.sendResourceTypes(event.getPlayer());
        }

    }
}

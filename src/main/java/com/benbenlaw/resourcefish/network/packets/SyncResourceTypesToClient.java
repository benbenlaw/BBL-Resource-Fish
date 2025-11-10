package com.benbenlaw.resourcefish.network.packets;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

import java.util.List;

public record SyncResourceTypesToClient(List<ResourceType> entries) implements CustomPacketPayload {

   public static final Type<SyncResourceTypesToClient> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "sync_resource_types_to_client"));

   public static final IPayloadHandler<SyncResourceTypesToClient> HANDLER = (packet, context) -> {

       ResourceType.clear();

       for (ResourceType type : packet.entries) {
           ResourceType.REGISTRY.put(type.getId(), type);
       }
   };

   public static final StreamCodec<RegistryFriendlyByteBuf, SyncResourceTypesToClient> STREAM_CODEC = StreamCodec.composite(
           ResourceType.STREAM_CODEC.apply(ByteBufCodecs.list()), SyncResourceTypesToClient::entries,
           SyncResourceTypesToClient::new
    );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

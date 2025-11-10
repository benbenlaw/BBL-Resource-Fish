package com.benbenlaw.resourcefish.util;

import com.benbenlaw.core.recipe.ChanceResult;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public class ChanceResultStreamCodec {
    public static final StreamCodec<RegistryFriendlyByteBuf, ChanceResult> STREAM_CODEC =
        StreamCodec.composite(
            ItemStack.STREAM_CODEC, ChanceResult::stack,
            ByteBufCodecs.FLOAT, ChanceResult::chance,
                ChanceResult::new
        );
}
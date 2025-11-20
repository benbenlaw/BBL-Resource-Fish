package com.benbenlaw.resourcefish.util;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.network.BiggerStreamCodec;
import com.benbenlaw.resourcefish.network.packets.SyncResourceTypesToClient;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResourceType {
    public static final Map<ResourceLocation, ResourceType> REGISTRY = new HashMap<>();
    private static final List<ChanceResult> EMPTY_DROP_ITEMS = new ArrayList<>();
    private final List<ResourceFishEntity.Pattern> patterns;
    private final List<ResourceFishEntity.Pattern.Base> models;
    private final List<String> biomes;

    public static void clear() {
        REGISTRY.clear();
    }

    public static final ResourceType NONE = new ResourceType(
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "none"),
            0xFFFFFF,
            0x000000,
            EMPTY_DROP_ITEMS,
            20 * 30, // 20 ticks per second * 30 seconds,
            List.of(),
            List.of(),
            List.of()

    );

    private final ResourceLocation id;
    private final int mainColor;
    private final int patternColor;
    private List<ChanceResult> dropItems;
    private final int dropIntervalTicks;

    public ResourceType(ResourceLocation id, int mainColor, int patternColor, List<ChanceResult> dropItems, int dropIntervalTicks,
                        List<ResourceFishEntity.Pattern> patterns, List<ResourceFishEntity.Pattern.Base> models, List<String> biomes) {
        this.id = id;
        this.mainColor = mainColor;
        this.patternColor = patternColor;
        this.dropItems = dropItems;
        this.dropIntervalTicks = dropIntervalTicks;
        this.patterns = patterns;
        this.models = models;
        this.biomes = biomes;

    }

    public ResourceLocation getId() {
        return id;
    }

    public int getColor() {
        return mainColor;
    }

    public int getPatternColor() {
        return patternColor;
    }

    public List<ChanceResult> getDropItems() {
        return dropItems;
    }

    public List<ItemStack> getResults() {
        return getRollResults().stream()
                .map(ChanceResult::stack)
                .collect(Collectors.toList());
    }

    public List<ResourceFishEntity.Pattern> getPatterns() {
        return patterns;
    }

    public List<ResourceFishEntity.Pattern.Base> getModels() {
        return models;
    }

    public List<String> getBiomes() {
        return biomes;
    }

    public List<ChanceResult> getRollResults() {
        return this.dropItems;
    }

    public List<ItemStack> rollResults(RandomSource rand) {
        List<ItemStack> results = new ArrayList<>();
        for (ChanceResult output : getRollResults()) {
            ItemStack stack = output.rollOutput(rand);
            if (!stack.isEmpty()) {
                results.add(stack);
            }
        }
        return results;
    }

    public int getDropIntervalTicks() {
        return dropIntervalTicks;
    }

    public static void register(ResourceType type) {
        REGISTRY.put(type.getId(), type);
    }

    public static ResourceType get(ResourceLocation id) {
        return REGISTRY.getOrDefault(id, REGISTRY.get(ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "none")));
    }

    public static Collection<ResourceType> getAll() {
        return REGISTRY.values();
    }

    public static final Codec<ResourceType> CODEC = RecordCodecBuilder.create(resourceTypeInstance -> resourceTypeInstance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(ResourceType::getId),
            Codec.INT.fieldOf("main_color").forGetter(ResourceType::getColor),
            Codec.INT.fieldOf("pattern_color").forGetter(ResourceType::getPatternColor),
            ChanceResult.CODEC.listOf().fieldOf("drop_items").forGetter(ResourceType::getDropItems),
            Codec.INT.fieldOf("drop_interval_ticks").forGetter(ResourceType::getDropIntervalTicks),
            ResourceFishEntity.Pattern.CODEC.listOf().fieldOf("patterns").forGetter(ResourceType::getPatterns),
            ResourceFishEntity.Pattern.Base.CODEC.listOf().fieldOf("models").forGetter(ResourceType::getModels),
            Codec.STRING.listOf().fieldOf("biomes").forGetter(ResourceType::getBiomes)
    ).apply(resourceTypeInstance, ResourceType::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ResourceType> STREAM_CODEC =
            BiggerStreamCodec.composite(
                    ResourceLocation.STREAM_CODEC, ResourceType::getId,
                    ByteBufCodecs.INT, ResourceType::getColor,
                    ByteBufCodecs.INT, ResourceType::getPatternColor,
                    ChanceResultStreamCodec.STREAM_CODEC.apply(ByteBufCodecs.list()), ResourceType::getDropItems,
                    ByteBufCodecs.INT, ResourceType::getDropIntervalTicks,
                    ResourceFishEntity.Pattern.STREAM_CODEC.apply(ByteBufCodecs.list()), ResourceType::getPatterns,
                    ResourceFishEntity.Pattern.Base.STREAM_CODEC.apply(ByteBufCodecs.list()), ResourceType::getModels,
                    ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), ResourceType::getBiomes,
                    ResourceType::new);



    public static void sendResourceTypes(ServerPlayer player) {;

        PacketDistributor.sendToPlayer(player, new SyncResourceTypesToClient(REGISTRY.values().stream().toList()));
    }


}
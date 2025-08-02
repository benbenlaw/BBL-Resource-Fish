package com.benbenlaw.resourcefish.util;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class ResourceType {
    public static final Map<ResourceLocation, ResourceType> REGISTRY = new HashMap<>();
    private static final Map<ResourceType, ItemStack> DROP_MAP = new HashMap<>();
    private static final List<ChanceResult> EMPTY_DROP_ITEMS = new ArrayList<>();
    private final List<ResourceFishEntity.Pattern> patterns;
    private final List<ResourceFishEntity.Pattern.Base> models;

    public static final ResourceType NONE = new ResourceType(
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "none"),
            0xFFFFFF,
            0x000000,
            EMPTY_DROP_ITEMS,
            20 * 30, // 20 ticks per second * 30 seconds,
            List.of(),
            List.of()
    );

    private final ResourceLocation id;
    private final int mainColor;
    private final int patternColor;
    private List<ChanceResult> dropItems;
    private final int dropIntervalTicks;

    public ResourceType(ResourceLocation id, int mainColor, int patternColor, List<ChanceResult> dropItems, int dropIntervalTicks,
                        List<ResourceFishEntity.Pattern> patterns, List<ResourceFishEntity.Pattern.Base> models) {
        this.id = id;
        this.mainColor = mainColor;
        this.patternColor = patternColor;
        this.dropItems = dropItems;
        this.dropIntervalTicks = dropIntervalTicks;
        this.patterns = patterns;
        this.models = models;

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
}
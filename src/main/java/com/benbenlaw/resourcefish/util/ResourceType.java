package com.benbenlaw.resourcefish.util;

import com.benbenlaw.resourcefish.ResourceFish;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResourceType {
    public static final Map<ResourceLocation, ResourceType> REGISTRY = new HashMap<>();
    private static final Map<ResourceType, ItemStack> DROP_MAP = new HashMap<>();

    public static final ResourceType NONE = new ResourceType(
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "none"),
            0xFFFFFF,
            ItemStack.EMPTY,
            20 * 30 // 20 ticks per second * 30 seconds
    );

    private final ResourceLocation id;
    private final int color;
    private final ItemStack dropItem;
    private final int dropIntervalTicks;

    public ResourceType(ResourceLocation id, int color, ItemStack dropItem, int dropIntervalTicks) {
        this.id = id;
        this.color = color;
        this.dropItem = dropItem;
        this.dropIntervalTicks = dropIntervalTicks;

    }

    public static void registerDrop(ResourceType resourceType, ItemStack drop) {
        DROP_MAP.put(resourceType, drop);
    }

    public static ItemStack getDropForResourceType(ResourceType resourceType) {
        return resourceType != null ? resourceType.getDropItem().copy() : ItemStack.EMPTY;
    }

    public ResourceLocation getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public ItemStack getDropItem() {
        return dropItem;
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
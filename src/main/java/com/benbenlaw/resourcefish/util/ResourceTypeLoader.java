package com.benbenlaw.resourcefish.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class ResourceTypeLoader extends SimpleJsonResourceReloadListener {

    public ResourceTypeLoader(Gson gson, String directory) {
        super(gson, directory);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        ResourceType.REGISTRY.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement element = entry.getValue();

            try {
                JsonObject obj = element.getAsJsonObject();

                if (!obj.has("color")) {
                    throw new IllegalArgumentException("Missing 'color' field for ResourceType: " + id);
                }

                int color = Integer.decode(obj.get("color").getAsString());

                if (!obj.has("drop_item")) {
                    throw new IllegalArgumentException("Missing 'drop_item' field for ResourceType: " + id);
                }

                JsonElement dropItemJson = obj.get("drop_item");
                DataResult<Pair<ItemStack, JsonElement>> result = ItemStack.CODEC.decode(JsonOps.INSTANCE, dropItemJson);
                ItemStack dropStack = result.getOrThrow().getFirst();

                // Optional: allow "drop_interval" to be missing and fallback to 30 seconds (600 ticks)
                int dropIntervalTicks = obj.has("drop_interval") ? obj.get("drop_interval").getAsInt() : 600;

                ResourceType.register(new ResourceType(id, color, dropStack, dropIntervalTicks));

            } catch (Exception e) {
                System.out.println("Failed to load ResourceType " + id + ": " + e.getMessage());
            }
        }

        System.out.println("Loaded " + ResourceType.REGISTRY.size() + " ResourceTypes");
    }
}
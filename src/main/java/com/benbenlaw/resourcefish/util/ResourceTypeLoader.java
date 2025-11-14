package com.benbenlaw.resourcefish.util;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceTypeLoader extends SimpleJsonResourceReloadListener {

    public ResourceTypeLoader(Gson gson, String directory) {
        super(gson, directory);
    }
    public ICondition.IContext context;

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap,
                         ResourceManager resourceManager,
                         ProfilerFiller profiler) {

        ResourceType.REGISTRY.clear();

        for (var entry : jsonMap.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonObject obj = entry.getValue().getAsJsonObject();

            try {

                RegistryOps<JsonElement> ops = this.makeConditionalOps();

                boolean willLoad = ICondition.conditionsMatched(ops, obj);

                if (!willLoad) {
                    System.out.println("Skipping ResourceType " + id + " due to conditions not met.");
                    continue;
                }

                if (!obj.has("main_color"))
                    throw new IllegalArgumentException("Missing 'main_color' for " + id);
                int mainColor = (int) Long.decode(fixColorHex(obj.get("main_color").getAsString())).longValue();

                if (!obj.has("pattern_color"))
                    throw new IllegalArgumentException("Missing 'pattern_color' for " + id);
                int patternColor = (int) Long.decode(fixColorHex(obj.get("pattern_color").getAsString())).longValue();

                if (!obj.has("drop_items"))
                    throw new IllegalArgumentException("Missing 'drop_items' for " + id);

                DataResult<List<ChanceResult>> dropsResult =
                        ChanceResult.CODEC.listOf().parse(JsonOps.INSTANCE, obj.get("drop_items"));

                List<ChanceResult> dropItems = dropsResult
                        .result()
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Invalid drop_items for " + id + ": " +
                                        dropsResult.error().map(err -> err.message()).orElse("Unknown error")
                        ));

                int dropIntervalTicks = obj.has("drop_interval") ? obj.get("drop_interval").getAsInt() : 600;

                // patterns
                List<ResourceFishEntity.Pattern> patterns = new ArrayList<>();
                if (obj.has("patterns")) {
                    for (JsonElement pat : obj.getAsJsonArray("patterns")) {
                        try {
                            patterns.add(ResourceFishEntity.Pattern.valueOf(pat.getAsString().toUpperCase()));
                        } catch (Exception e) {
                            System.out.println("Invalid pattern for " + id + ": " + pat.getAsString());
                        }
                    }
                }

                // models
                List<ResourceFishEntity.Pattern.Base> models = new ArrayList<>();
                if (obj.has("models")) {
                    for (JsonElement model : obj.getAsJsonArray("models")) {
                        try {
                            models.add(ResourceFishEntity.Pattern.Base.valueOf(model.getAsString().toUpperCase()));
                        } catch (Exception e) {
                            System.out.println("Invalid model for " + id + ": " + model.getAsString());
                        }
                    }
                }

                // biomes
                List<String> biomes = new ArrayList<>();
                if (obj.has("biomes")) {
                    for (JsonElement b : obj.getAsJsonArray("biomes")) {
                        biomes.add(b.getAsString());
                    }
                }

                // Register it
                ResourceType.register(
                        new ResourceType(id, mainColor, patternColor, dropItems, dropIntervalTicks, patterns, models, biomes)
                );

                System.out.println("Loaded ResourceType " + id);

            } catch (Exception e) {
                System.out.println("Failed to load ResourceType " + id + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Loaded " + ResourceType.REGISTRY.size() + " ResourceTypes");
    }

    private String fixColorHex(String hex) {
        if (!hex.startsWith("#") && !hex.startsWith("0x") && !hex.startsWith("0X")) {
            return "0x" + hex;
        }
        return hex;
    }

}
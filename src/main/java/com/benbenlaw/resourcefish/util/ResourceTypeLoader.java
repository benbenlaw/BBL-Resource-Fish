package com.benbenlaw.resourcefish.util;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
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
    protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
        ResourceType.REGISTRY.clear();

        for (Map.Entry<ResourceLocation, JsonElement> entry : jsonMap.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement element = entry.getValue();
            RegistryOps<JsonElement> registryOps = this.makeConditionalOps();

            try {

                boolean willLoad = true;

                JsonObject obj = element.getAsJsonObject();

                if (obj.has("neoforge:conditions")) {
                    var conditions = ICondition.LIST_CODEC.decode(registryOps, obj.getAsJsonArray("neoforge:conditions"));

                    if (conditions.result().isPresent()) {
                        for (ICondition condition : conditions.result().get().getFirst()) {
                            if (!condition.test(ICondition.IContext.EMPTY)) {
                                System.out.println("Condition failed: " + condition + " for " + id);
                                willLoad = false;
                            }
                        }
                    } else {
                        System.out.println("Failed to parse conditions for " + id + ": " +
                                conditions.error().map(err -> err.message()).orElse("Unknown error"));
                        willLoad = false;
                    }
                }

                if (willLoad) {

                    if (!obj.has("main_color")) {
                        throw new IllegalArgumentException("Missing 'main_color' field for ResourceType: " + id);
                    }

                    int mainColor = (int) Long.decode(fixColorHex(obj.get("main_color").getAsString())).longValue();

                    if (!obj.has("pattern_color")) {
                        throw new IllegalArgumentException("Missing 'pattern_color' field for ResourceType: " + id);
                    }

                    int patternColor = (int) Long.decode(fixColorHex(obj.get("pattern_color").getAsString())).longValue();

                    if (!obj.has("drop_items")) {
                        throw new IllegalArgumentException("Missing 'drop_items' field for ResourceType: " + id);
                    }

                    JsonElement dropItemsJson = obj.get("drop_items");
                    DataResult<List<ChanceResult>> result = ChanceResult.CODEC.listOf().parse(JsonOps.INSTANCE, dropItemsJson);
                    List<ChanceResult> dropItems = result
                            .result()
                            .orElseThrow(() -> new IllegalArgumentException(
                                    "Invalid drop_items for " + id + ": " +
                                            result.error().map(err -> err.message()).orElse("Unknown error")));

                    // Optional: allow "drop_interval" to be missing and fallback to 30 seconds (600 ticks)
                    int dropIntervalTicks = obj.has("drop_interval") ? obj.get("drop_interval").getAsInt() : 600;

                    List<ResourceFishEntity.Pattern> patterns = new ArrayList<>();
                    if (obj.has("patterns")) {
                        for (JsonElement patternElement : obj.getAsJsonArray("patterns")) {
                            try {
                                ResourceFishEntity.Pattern pattern = ResourceFishEntity.Pattern.valueOf(patternElement.getAsString().toUpperCase());
                                patterns.add(pattern);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid pattern '" + patternElement.getAsString() + "' for ResourceType " + id + ": " + e.getMessage());
                            }
                        }
                    }

                    List<ResourceFishEntity.Pattern.Base> models = new ArrayList<>();
                    if (obj.has("models")) {
                        for (JsonElement modelElement : obj.getAsJsonArray("models")) {
                            try {
                                ResourceFishEntity.Pattern.Base model = ResourceFishEntity.Pattern.Base.valueOf(modelElement.getAsString().toUpperCase());
                                models.add(model);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Invalid model '" + modelElement.getAsString() + "' for ResourceType " + id + ": " + e.getMessage());
                            }
                        }
                    }

                    List<String> biomes = new ArrayList<>();
                    if (obj.has("biomes")) {
                        for (JsonElement biomeElement : obj.getAsJsonArray("biomes")) {
                            String biomeId = biomeElement.getAsString();
                            if (biomeId != null) {
                                biomes.add(biomeId);
                            } else {
                                System.out.println("Invalid biome ID '" + biomeElement.getAsString() + "' for ResourceType " + id);
                            }
                        }
                    }

                    ResourceType.register(new ResourceType(id, mainColor, patternColor, dropItems, dropIntervalTicks, patterns, models, biomes));
                    System.out.println("Loaded ResourceType " + id + " with main color " + mainColor + ", pattern color " + patternColor +
                            ", drop interval " + dropIntervalTicks + " ticks, patterns: " + patterns + ", models: " + models);
               } else {
                    System.out.println("Skipping ResourceType " + id + " due to conditions not met.");
                }

            } catch (Exception e) {
                System.out.println("Failed to load ResourceType " + id + ": " + e.getMessage());
                e.printStackTrace(); // Add this line to see full error and stack trace
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
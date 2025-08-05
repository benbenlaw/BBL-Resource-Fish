package com.benbenlaw.resourcefish.data.builders;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ResourceFishBuilder implements DataProvider {

    private final PackOutput output;
    private final List<FishDefinition> fishes = new ArrayList<>();

    public ResourceFishBuilder(PackOutput output) {
        this.output = output;
    }

    public ResourceFishBuilder addFish(FishDefinition fish) {
        this.fishes.add(fish);
        return this;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Path outputFolder = output.getOutputFolder(PackOutput.Target.DATA_PACK);

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (FishDefinition fish : fishes) {
            JsonObject json = new JsonObject();

            json.addProperty("main_color", String.format("#%06X", (0xFFFFFF & fish.mainColor)));
            json.addProperty("pattern_color", String.format("#%06X", (0xFFFFFF & fish.patternColor)));

            JsonArray dropItems = new JsonArray();
            for (ChanceResult result : fish.dropItems) {
                dropItems.add(ChanceResult.CODEC.encodeStart(com.mojang.serialization.JsonOps.INSTANCE, result).result().orElseThrow());
            }
            json.add("drop_items", dropItems);

            json.addProperty("drop_interval", fish.dropIntervalTicks);

            if (!fish.patterns.isEmpty()) {
                JsonArray patterns = new JsonArray();
                for (ResourceFishEntity.Pattern pattern : fish.patterns) {
                    patterns.add(pattern.name().toLowerCase());
                }
                json.add("patterns", patterns);
            }

            if (!fish.models.isEmpty()) {
                JsonArray models = new JsonArray();
                for (ResourceFishEntity.Pattern.Base model : fish.models) {
                    models.add(model.name().toLowerCase());
                }
                json.add("models", models);
            }

            if (!fish.biomes.isEmpty()) {
                JsonArray biomes = new JsonArray();
                for (String biome : fish.biomes) {
                    biomes.add(biome);
                }
                json.add("biomes", biomes);
            }


            Path path = outputFolder.resolve(fish.id.getNamespace() + "/fish/" + fish.id.getPath() + ".json");

            futures.add(DataProvider.saveStable(cache, json, path));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public String getName() {
        return "Resource Fish Builder";
    }

    /** Helper class to define a fish. */
    public static class FishDefinition {
        public final ResourceLocation id;
        public final int mainColor;
        public final int patternColor;
        public final List<ChanceResult> dropItems;
        public final int dropIntervalTicks;
        public final List<ResourceFishEntity.Pattern> patterns;
        public final List<ResourceFishEntity.Pattern.Base> models;
        public final List<String> biomes;


        public FishDefinition(ResourceLocation id, int mainColor, int patternColor, List<ChanceResult> dropItems,
                              int dropIntervalTicks, List<ResourceFishEntity.Pattern> patterns,
                              List<ResourceFishEntity.Pattern.Base> models, List<String> biomes) {
            this.id = id;
            this.mainColor = mainColor;
            this.patternColor = patternColor;
            this.dropItems = dropItems;
            this.dropIntervalTicks = dropIntervalTicks;
            this.patterns = patterns;
            this.models = models;
            this.biomes = biomes;
        }
    }
}
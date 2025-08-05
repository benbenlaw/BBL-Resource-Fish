package com.benbenlaw.resourcefish.data.builders;

import com.benbenlaw.core.recipe.ChanceResult;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ResourceTypeBuilder implements DataProvider {
    public String id;
    public String mainColor;
    public String patternColor;
    public List<ChanceResult> chanceResults;
    public int dropInterval = 600;
    public List<String> patterns = new ArrayList<>();
    public List<String> models = new ArrayList<>();
    public List<JsonObject> conditions = new ArrayList<>();

    @Override
    public CompletableFuture<?> run(CachedOutput p_236071_) {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}

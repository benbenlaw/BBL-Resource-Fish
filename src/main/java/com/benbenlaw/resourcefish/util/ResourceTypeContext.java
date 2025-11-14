package com.benbenlaw.resourcefish.util;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


public class ResourceTypeContext implements ICondition.IContext {
    private final RegistryAccess registryAccess;

    public ResourceTypeContext(RegistryAccess registryAccess) {
        this.registryAccess = registryAccess;
    }


    @Override
    public <T> Map<ResourceLocation, Collection<Holder<T>>> getAllTags(ResourceKey<? extends Registry<T>> registryKey) {
        Registry<T> registry = registryAccess.registryOrThrow(registryKey);

        return registry.getTags()
                .collect(Collectors.toMap(
                        pair -> pair.getFirst().location(),
                        pair -> pair.getSecond().stream().collect(Collectors.toList())
                ));
    }

    @Override
    public <T> Collection<Holder<T>> getTag(TagKey<T> key) {
        return registryAccess.registryOrThrow(key.registry())
                .getTag(key)
                .map(tagSet -> tagSet.stream().collect(Collectors.toList()))
                .orElseGet(java.util.Collections::emptyList);
    }
}
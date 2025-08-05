package com.benbenlaw.resourcefish.data;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.data.builders.ResourceFishBuilder;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.CaviarItem;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class ResourceFishFishes extends ResourceFishBuilder {

    public ResourceFishFishes(PackOutput output) {
        super(output);

        //Emerald Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "emerald"),
                0x50C878,
                0x006400,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("emerald"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Diamond Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "diamond"),
                0x00BFFF,
                0x1C1C1C,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("diamond"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Gold Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "gold"),
                0xFFD700,
                0xB8860B,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("gold"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Redstone Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "redstone"),
                0xFF0000,
                0x8B0000,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("redstone"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Lapis Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "lapis"),
                0x1E90FF,
                0x00008B,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("lapis"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Iron Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "iron"),
                0xD4D4D4,
                0x7A7A7A,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("iron"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_2, ResourceFishEntity.Pattern.SMALL_3),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Copper Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "copper"),
                0xB87333,
                0x427333,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("copper"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_1),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Coal Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "coal"),
                0x2F2F2F,
                0x1C1C1C,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("coal"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of()
        ));

        //Lava Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "lava"),
                0xFF4500,
                0x8B0000,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("lava"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_6),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of("#minecraft:is_ocean")
        ));

        //Wood Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "wood"),
                0x8B4513,
                0x5C3A29,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("wood"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_4, ResourceFishEntity.Pattern.SMALL_5),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of("#minecraft:is_ocean")
        ));

        //Stone Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "stone"),
                0xA9A9A9,
                0x696969,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("stone"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_6),
                List.of(),
                List.of()
        ));

        //Cobblestone Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "cobblestone"),
                0x808080,
                0x696969,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("cobblestone"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_5, ResourceFishEntity.Pattern.SMALL_6),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of("#minecraft:is_ocean")
        ));

        //Water Fish
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "water"),
                0x2C3F7A,
                0x3E5B77,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("water"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_4),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of("#minecraft:is_ocean")
        ));

        //Dirt
        this.addFish(new ResourceFishBuilder.FishDefinition(
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "dirt"),
                0x7F3300,
                0x7C3622,
                List.of(new ChanceResult(CaviarItem.createCaviarStack("dirt"), 1.0f)),
                300,
                List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2),
                List.of(ResourceFishEntity.Pattern.Base.SMALL),
                List.of("#minecraft:is_ocean")
        ));
    }
}

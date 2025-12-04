package com.benbenlaw.resourcefish.data;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.core.tag.CommonTags;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.data.builders.CaviarProcessorRecipeBuilder;
import com.benbenlaw.resourcefish.data.builders.ResourceFishBuilder;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.CaviarItem;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;

import java.util.ArrayList;
import java.util.List;

public class ResourceFishFishes extends ResourceFishBuilder {

    record FishData(String name, int mainColor, int patternColor, List<ChanceResult> drops, int dropInterval,
                        List<ResourceFishEntity.Pattern> patterns, List<ResourceFishEntity.Pattern.Base> models,
                        List<String> biomes, boolean withConditions) {}

    public ResourceFishFishes(PackOutput output) {
        super(output);

        List<FishData> fishData = new ArrayList<>();
        List<ResourceFishEntity.Pattern.Base> smallModels = List.of(ResourceFishEntity.Pattern.Base.SMALL);
        List<ResourceFishEntity.Pattern.Base> largeModels = List.of(ResourceFishEntity.Pattern.Base.LARGE);

        ResourceFishEntity.Pattern small1 = ResourceFishEntity.Pattern.SMALL_1;
        ResourceFishEntity.Pattern small2 = ResourceFishEntity.Pattern.SMALL_2;
        ResourceFishEntity.Pattern small3 = ResourceFishEntity.Pattern.SMALL_3;
        ResourceFishEntity.Pattern small4 = ResourceFishEntity.Pattern.SMALL_4;
        ResourceFishEntity.Pattern small5 = ResourceFishEntity.Pattern.SMALL_5;
        ResourceFishEntity.Pattern small6 = ResourceFishEntity.Pattern.SMALL_6;
        ResourceFishEntity.Pattern large1 = ResourceFishEntity.Pattern.LARGE_1;
        ResourceFishEntity.Pattern large2 = ResourceFishEntity.Pattern.LARGE_2;
        ResourceFishEntity.Pattern large3 = ResourceFishEntity.Pattern.LARGE_3;
        ResourceFishEntity.Pattern large4 = ResourceFishEntity.Pattern.LARGE_4;
        ResourceFishEntity.Pattern large5 = ResourceFishEntity.Pattern.LARGE_5;
        ResourceFishEntity.Pattern large6 = ResourceFishEntity.Pattern.LARGE_6;


        //Dirt
        fishData.add(new FishData("dirt", 0xFF7F3300, 0xFF7C3622, List.of(), 300,
                List.of(large5, large2), largeModels, List.of("#c:is_ocean"), false));

        //Water
        fishData.add(new FishData("water", 0x802C3F7A, 0x803E5B77, List.of(), 300,
                List.of(small3, small4), smallModels, List.of("#c:is_ocean"), false));

        //Lava
        fishData.add(new FishData("lava", 0x80FF4500, 0x808B0000, List.of(), 300,
                List.of(small3, small6), smallModels, List.of("#c:is_ocean"), false));

        //Cobblestone
        fishData.add(new FishData("cobblestone", 0xFF808080, 0xFF696969, List.of(), 300,
                List.of(large1, large2), largeModels, List.of("#c:is_ocean"), false));

        //Wood
        fishData.add(new FishData("wood", 0xFF8B4513, 0xFF5C3A29, List.of(), 300,
                List.of(large2, large3), largeModels, List.of("#c:is_ocean"), false));

        //Stone
        fishData.add(new FishData("stone", 0xFFA9A9A9, 0xFF696969, List.of(), 300,
                List.of(large3, large6), largeModels, List.of(), false));

        //Granite
        fishData.add(new FishData("granite", 0xFFA77663, 0xFFA77663, List.of(), 300,
                List.of(large4, large5), largeModels, List.of(), false));

        //Diorite
        fishData.add(new FishData("diorite", 0xFFF5F5DC, 0xFFA9A9A9, List.of(), 300,
                List.of(large1, large2), largeModels, List.of(), false));

        //Andesite
        fishData.add(new FishData("andesite", 0xFFD3D3D3, 0xFF696969, List.of(), 300,
                List.of(large2, large3), largeModels, List.of(), false));

        //Sand
        fishData.add(new FishData("sand", 0xFFF4A460, 0xFFDEB887, List.of(), 300,
                List.of(large2, large6), largeModels, List.of(), false));

        //Gravel
        fishData.add(new FishData("gravel", 0xFF808080, 0xFF696969, List.of(), 300,
                List.of(large3, large4), largeModels, List.of(), false));

        //Coal
        fishData.add(new FishData("coal", 0xFF2F2F2F, 0xFF1C1C1C, List.of(), 300,
                List.of(small1, small2), smallModels, List.of(), false));

        //Lapis
        fishData.add(new FishData("lapis", 0xFF1E90FF, 0xFF00008B, List.of(), 300,
                List.of(small1, small2), smallModels, List.of(), false));

        //Redstone
        fishData.add(new FishData("redstone", 0xFFFF0000, 0xFF8B0000, List.of(), 300,
                List.of(small1, small2), smallModels, List.of(), false));

        //Diamond
        fishData.add(new FishData("diamond", 0xFF00BFFF, 0xFF1C1C1C, List.of(), 300,
                List.of(small1, small2), smallModels, List.of(), false));

        //Emerald
        fishData.add(new FishData("emerald", 0xFF50C878, 0xFF006400, List.of(), 300,
                List.of(small1, small2), smallModels, List.of(), false));

        //Copper
        fishData.add(new FishData("copper", 0xFFB87333, 0xFF427333, List.of(), 300,
                List.of(small3, small1), smallModels, List.of(), false));

        //Iron
        fishData.add(new FishData("iron", 0xFFD4D4D4, 0xFF7A7A7A, List.of(), 300,
                List.of(small2, small3), smallModels, List.of(), false));

        //Gold
        fishData.add(new FishData("gold", 0xFFFFD700, 0xFFB8860B, List.of(), 300,
                List.of(small1, small2), smallModels, List.of(), false));

        //Netherrack
        fishData.add(new FishData("netherrack", 0xFF8B0000, 0xFF4B0000, List.of(), 300,
                List.of(large4, large5), largeModels, List.of(), false));

        //Soul Sand
        fishData.add(new FishData("soul_sand", 0xFF221A16, 0xFF786051, List.of(), 300,
                List.of(large2, large3), largeModels, List.of(), false));

        //Quartz
        fishData.add(new FishData("quartz", 0xFFFFFFFF, 0xFFBFBFBF, List.of(), 300,
                List.of(small4, small1), smallModels, List.of(), false));

        //Glowstone
        fishData.add(new FishData("glowstone", 0xFFFFFACD, 0xFFFFD700, List.of(), 300,
                List.of(small5, small6), smallModels, List.of(), false));

        //Zombie
        fishData.add(new FishData("zombie", 0x20A9A9A9, 0xFF6B8E23, List.of(), 300,
                List.of(small6), smallModels, List.of(), false));

        //Skeleton
        fishData.add(new FishData("skeleton", 0x20FFFFFF, 0xFF6B6B6B, List.of(), 300,
                List.of(small5), smallModels, List.of(), false));

        //Creeper
        fishData.add(new FishData("creeper", 0x20A9A9A9, 0xFF228B22, List.of(), 300,
                List.of(small4), smallModels, List.of(), false));

        //Spider
        fishData.add(new FishData("spider", 0x20A9A9A9, 0xFF8B4513, List.of(), 300,
                List.of(small2), smallModels, List.of(), false));

        //Colors
        for (DyeColor color : DyeColor.values()) {
            String colorName = color.getSerializedName();
            int rgb = color.getFireworkColor();
            int colorInt = 0xFF000000 | rgb;

            fishData.add(new FishData(colorName, colorInt, colorInt, List.of(), 300,
                    List.of(small1), smallModels, List.of(), false));

        }

        //Tin
        fishData.add(new FishData("tin", 0xFFB0C4DE, 0xFF708090, List.of(), 300,
                List.of(small5, small1), smallModels, List.of(), true));

        //Aluminum
        fishData.add(new FishData("aluminum", 0xFFC0C0C0, 0xFF808080, List.of(), 300,
                List.of(small6, small2), smallModels, List.of(), true));

        //Lead
        fishData.add(new FishData("lead", 0xFF696969, 0xFF2F4F4F, List.of(), 300,
                List.of(small3, small4), smallModels, List.of(), true));

        //Nickel
        fishData.add(new FishData("nickel", 0xFFBFC1C2, 0xFF6E7F80, List.of(), 300,
                List.of(small2, small5), smallModels, List.of(), true));

        //Osmium
        fishData.add(new FishData("osmium", 0xFF74A9C1, 0xFF2E5984, List.of(), 300,
                List.of(small4, small6), smallModels, List.of(), true));

        //Platinum
        fishData.add(new FishData("platinum", 0xFFE5E4E2, 0xFF8E8E8E, List.of(), 300,
                List.of(small1, small3), smallModels, List.of(), true));

        //Silver
        fishData.add(new FishData("silver", 0xFFC0C0C0, 0xFFB0B0B0, List.of(), 300,
                List.of(small5, small2), smallModels, List.of(), true));

        //Uranium
        fishData.add(new FishData("uranium", 0xFF7FFF00, 0xFF556B2F, List.of(), 300,
                List.of(small6, small4), smallModels, List.of(), true));

        //Zinc
        fishData.add(new FishData("zinc", 0xFFB0C4DE, 0xFF708090, List.of(), 300,
                List.of(small3, small1), smallModels, List.of(), true));

        //Iridium
        fishData.add(new FishData("iridium", 0xFF3D3C3A, 0xFF1C1C1C, List.of(), 300,
                List.of(small2, small6), smallModels, List.of(), true));



        //Ruby
        fishData.add(new FishData("ruby", 0xFFAA0114, 0xFF7B0000, List.of(), 300,
                List.of(small1, small4), smallModels, List.of(), true));

        //Sapphire
        fishData.add(new FishData("sapphire", 0xFF0F52BA, 0xFF00008B, List.of(), 300,
                List.of(small5, small3), smallModels, List.of(), true));

        //Peridot
        fishData.add(new FishData("peridot", 0xFFB4C424, 0xFF6E8B3D, List.of(), 300,
                List.of(small6, small2), smallModels, List.of(), true));

        //Amethyst
        fishData.add(new FishData("amethyst", 0xFF9966CC, 0xFF4B0082, List.of(), 300,
                List.of(small4, small1), smallModels, List.of(), false));

        /*

        //Sulfur
        fishData.add(new FishData("sulfur", 0xFFFFF700, 0xFFB5A900, List.of(), 300,
                List.of(small3, small5), smallModels, List.of(), false));

        //Fluorite
        fishData.add(new FishData("fluorite", 0xFF7FFFD4, 0xFF008B8B, List.of(), 300,
                List.of(small2, small6), smallModels, List.of(), true));

        //Cinnabar
        fishData.add(new FishData("cinnabar", 0xFFB22222, 0xFF8B0000, List.of(), 300,
                List.of(small1, small4), smallModels, List.of(), true));

        */

        //Basalt
        fishData.add(new FishData("basalt", 0xFF2C2C2C, 0xFF1A1A1A, List.of(), 300,
                List.of(large6, large3), largeModels, List.of(), false));

        //Ancient
        fishData.add(new FishData("ancient", 0xFF908785, 0xFF958986, List.of(), 300,
                List.of(large5, large1), largeModels, List.of(), false));

        //Obsidian
        fishData.add(new FishData("obsidian", 0xFF1C1C1C, 0xFF0B0B0B, List.of(), 300,
                List.of(large4, large2), largeModels, List.of(), false));

        //Blaze Fish
        fishData.add(new FishData("blaze", 0x80FF8C00, 0x80FF4500, List.of(), 300,
                List.of(small5, small6), smallModels, List.of(), false));


        for (FishData data : fishData) {

            ChanceResult addCaviarStack = new ChanceResult(CaviarItem.createCaviarStack(data.name), 1.0f);
            List<ChanceResult> totalDrops = new ArrayList<>(data.drops);
            totalDrops.add(addCaviarStack);

            var fishBuilder = ResourceFishBuilder.builder(data.name)
                    .mainColor(data.mainColor)
                    .patternColor(data.patternColor)
                    .dropItems(totalDrops)
                    .dropInterval(data.dropInterval)
                    .patterns(data.patterns)
                    .models(data.models)
                    .biomes(data.biomes);

            if (data.withConditions) {
                fishBuilder.conditions(List.of(new NotCondition(new TagEmptyCondition(CommonTags.getTag(data.name, CommonTags.ResourceType.ORES)))));
            }

            this.addFish(fishBuilder.build());
        }
    }
}

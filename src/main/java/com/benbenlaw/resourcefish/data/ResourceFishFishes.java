package com.benbenlaw.resourcefish.data;

import com.benbenlaw.core.recipe.ChanceResult;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.data.builders.CaviarProcessorRecipeBuilder;
import com.benbenlaw.resourcefish.data.builders.ResourceFishBuilder;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.CaviarItem;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.NonNullList;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.ItemExistsCondition;

import java.util.List;

public class ResourceFishFishes extends ResourceFishBuilder {

    public ResourceFishFishes(PackOutput output) {
        super(output);

        //Granite Fish
        this.addFish(ResourceFishBuilder.builder("granite")
                .mainColor(0xFFA77663)
                .patternColor(0xFFA77663)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("granite"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());


        //Diorite Fish
        this.addFish(ResourceFishBuilder.builder("diorite")
                .mainColor(0xFFF5F5DC)
                .patternColor(0xFFA9A9A9)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("diorite"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Andesite Fish
        this.addFish(ResourceFishBuilder.builder("andesite")
                .mainColor(0xFFD3D3D3)
                .patternColor(0xFF696969)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("andesite"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Emerald Fish
        this.addFish(ResourceFishBuilder.builder("emerald")
                .mainColor(0xFF50C878)
                .patternColor(0xFF006400)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("emerald"), 1.0f)))
                .dropInterval(300)
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Diamond Fish
        this.addFish(ResourceFishBuilder.builder("diamond")
                .mainColor(0xFF00BFFF)
                .patternColor(0xFF1C1C1C)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("diamond"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Gold Fish
        this.addFish(ResourceFishBuilder.builder("gold")
                .mainColor(0xFFFFD700)
                .patternColor(0xFFB8860B)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("gold"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Redstone Fish
        this.addFish(ResourceFishBuilder.builder("redstone")
                .mainColor(0xFFFF0000)
                .patternColor(0xFF8B0000)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("redstone"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Lapis Fish
        this.addFish(ResourceFishBuilder.builder("lapis")
                .mainColor(0xFF1E90FF)
                .patternColor(0xFF00008B)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("lapis"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Iron
        this.addFish(ResourceFishBuilder.builder("iron")
                .mainColor(0xFFD4D4D4)
                .patternColor(0xFF7A7A7A)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("iron"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_2, ResourceFishEntity.Pattern.SMALL_3))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());
        //Copper Fish
        this.addFish(ResourceFishBuilder.builder("copper")
                .mainColor(0xFFB87333)
                .patternColor(0xFF427333)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("copper"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_1))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Coal Fish
        this.addFish(ResourceFishBuilder.builder("coal")
                .mainColor(0xFF2F2F2F)
                .patternColor(0xFF1C1C1C)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("coal"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Lava Fish

        this.addFish(ResourceFishBuilder.builder("lava")
                .mainColor(0x80FF4500)
                .patternColor(0x808B0000)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("lava"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_6))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .biomes(List.of("#c:is_ocean"))
                .build());


        //Wood Fish
        this.addFish(ResourceFishBuilder.builder("wood")
                .mainColor(0xFF8B4513)
                .patternColor(0xFF5C3A29)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("wood"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_4, ResourceFishEntity.Pattern.SMALL_5))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .biomes(List.of("#c:is_ocean"))
                .build());

        //Stone Fish
        this.addFish(ResourceFishBuilder.builder("stone")
                .mainColor(0xFFA9A9A9)
                .patternColor(0xFF696969)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("stone"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_6))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        CaviarProcessorRecipeBuilder.caviarProcessorRecipeBuilder(CaviarItem.createCaviarStack("stone"),
                NonNullList.of(new ChanceResult(new ItemStack(Items.STONE), 0.2f)));

        //Cobblestone Fish
        this.addFish(ResourceFishBuilder.builder("cobblestone")
                .mainColor(0xFF808080)
                .patternColor(0xFF696969)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("cobblestone"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_5, ResourceFishEntity.Pattern.SMALL_6))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .biomes(List.of("#c:is_ocean"))
                .build());

        //Water Fish
        this.addFish(ResourceFishBuilder.builder("water")
                .mainColor(0x802C3F7A)
                .patternColor(0x803E5B77)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("water"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_3, ResourceFishEntity.Pattern.SMALL_4))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .biomes(List.of("#c:is_ocean"))
                .build());

        //Dirt
        this.addFish(ResourceFishBuilder.builder("dirt")
                .mainColor(0xFF7F3300)
                .patternColor(0xFF7C3622)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("dirt"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_1, ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .biomes(List.of("#c:is_ocean"))
                .build());

        //Netherrack
        this.addFish(ResourceFishBuilder.builder("netherrack")
                .mainColor(0xFF8B0000)
                .patternColor(0xFF4B0000)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("netherrack"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_4, ResourceFishEntity.Pattern.SMALL_5))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Soul Sand
        this.addFish(ResourceFishBuilder.builder("soul_sand")
                .mainColor(0xFF221A16)
                .patternColor(0xFF786051)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("soul_sand"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_2, ResourceFishEntity.Pattern.SMALL_3))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Skeleton
        this.addFish(ResourceFishBuilder.builder("skeleton")
                .mainColor(0x20FFFFFF)
                .patternColor(0xFF6B6B6B)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("skeleton"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_5))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Zombie
        this.addFish(ResourceFishBuilder.builder("zombie")
                .mainColor(0x20A9A9A9)
                .patternColor(0xFF6B8E23)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("zombie"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_6))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Creeper
        this.addFish(ResourceFishBuilder.builder("creeper")
                .mainColor(0x20A9A9A9)
                .patternColor(0xFF228B22)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("creeper"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_4))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());

        //Spider
        this.addFish(ResourceFishBuilder.builder("spider")
                .mainColor(0x20A9A9A9)
                .patternColor(0xFF8B4513)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("spider"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_2))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());
        //Ender
        this.addFish(ResourceFishBuilder.builder("ender")
                .mainColor(0xFF1C685A)
                .patternColor(0xFF00008B)
                .dropItems(List.of(new ChanceResult(CaviarItem.createCaviarStack("ender"), 1.0f)))
                .dropInterval(300)
                .patterns(List.of(ResourceFishEntity.Pattern.SMALL_3))
                .models(List.of(ResourceFishEntity.Pattern.Base.SMALL))
                .build());



    }
}

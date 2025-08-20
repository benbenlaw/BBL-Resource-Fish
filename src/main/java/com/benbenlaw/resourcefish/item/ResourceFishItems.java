package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ResourceFishItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ResourceFish.MOD_ID);

    public static final DeferredItem<Item> DEPTH_UPGRADE_1 = ITEMS.register("depth_upgrade_1",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DEPTH_UPGRADE_2 = ITEMS.register("depth_upgrade_2",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DEPTH_UPGRADE_3 = ITEMS.register("depth_upgrade_3",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WIDTH_UPGRADE_1 = ITEMS.register("width_upgrade_1",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WIDTH_UPGRADE_2 = ITEMS.register("width_upgrade_2",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> WIDTH_UPGRADE_3 = ITEMS.register("width_upgrade_3",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SPEED_UPGRADE_1 = ITEMS.register("speed_upgrade_1",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SPEED_UPGRADE_2 = ITEMS.register("speed_upgrade_2",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SPEED_UPGRADE_3 = ITEMS.register("speed_upgrade_3",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SPEED_UPGRADE_4 = ITEMS.register("speed_upgrade_4",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SPEED_UPGRADE_5 = ITEMS.register("speed_upgrade_5",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TANK_UPGRADE = ITEMS.register("tank_upgrade",
            () -> new Item(new Item.Properties()));





    public static final DeferredItem<Item> RESOURCE_FISH_BUCKET = ITEMS.register("resource_fish_bucket",
            () -> new ResourceFishBucketItem(
                    ResourceFishEntities.RESOURCE_FISH.get(),
                    Fluids.WATER,
                    SoundEvents.BUCKET_FILL_FISH,
                    new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)
                            .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> RESOURCE_FISH_SPAWN_EGG = ITEMS.register("resource_fish_spawn_egg",
            () -> new ResourceFishSpawnEgg(
                    ResourceFishEntities.RESOURCE_FISH,
                    10064737,
                    10064737,
                    new Item.Properties()));

    public static final DeferredItem<Item> CAVIAR = ITEMS.register("caviar",
            () -> new CaviarItem(new Item.Properties()
                    .food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build())));



}




package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ResourceFishItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ResourceFish.MOD_ID);

    public static final DeferredItem<Item> RESOURCE_FISH_BUCKET = ITEMS.register("resource_fish_bucket",
            () -> new ResourceFishBucketItem(
                    ResourceFishEntities.RESOURCE_FISH.get(),
                    Fluids.WATER,
                    SoundEvents.BUCKET_FILL_FISH,
                    new Item.Properties().stacksTo(1).craftRemainder(Items.BUCKET)
                            .component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

}




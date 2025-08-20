package com.benbenlaw.resourcefish.data;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ResourceFishItemModelsProvider extends ItemModelProvider {
    public ResourceFishItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ResourceFish.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //Misc Items
        simpleItem(ResourceFishItems.RESOURCE_FISH_BUCKET);
        simpleItem(ResourceFishItems.DEPTH_UPGRADE_1);
        simpleItem(ResourceFishItems.DEPTH_UPGRADE_2);
        simpleItem(ResourceFishItems.DEPTH_UPGRADE_3);
        simpleItem(ResourceFishItems.WIDTH_UPGRADE_1);
        simpleItem(ResourceFishItems.WIDTH_UPGRADE_2);
        simpleItem(ResourceFishItems.WIDTH_UPGRADE_3);
        simpleItem(ResourceFishItems.SPEED_UPGRADE_1);
        simpleItem(ResourceFishItems.SPEED_UPGRADE_2);
        simpleItem(ResourceFishItems.SPEED_UPGRADE_3);
        simpleItem(ResourceFishItems.SPEED_UPGRADE_4);
        simpleItem(ResourceFishItems.SPEED_UPGRADE_5);
        simpleItem(ResourceFishItems.TANK_UPGRADE);
    }

    private void simpleItem(DeferredItem<Item> item) {
        withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "item/" + item.getId().getPath()));
    }

    @Override
    public String getName() {
        return ResourceFish.MOD_ID + " Item Models";
    }
}

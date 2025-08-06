package com.benbenlaw.resourcefish.data;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.util.ResourceFishTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ResourceFishItemTags extends ItemTagsProvider {

    ResourceFishItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags.contentsGetter(), ResourceFish.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //Upgrades
        tag(ResourceFishTags.Items.UPGRADES)
                .add(ResourceFishItems.DEPTH_UPGRADE_1.get())
                .add(ResourceFishItems.DEPTH_UPGRADE_2.get())
                .add(ResourceFishItems.DEPTH_UPGRADE_3.get())
                .add(ResourceFishItems.WIDTH_UPGRADE_1.get())
                .add(ResourceFishItems.WIDTH_UPGRADE_2.get())
                .add(ResourceFishItems.WIDTH_UPGRADE_3.get());


    }


    @Override
    public @NotNull String getName() {
        return ResourceFish.MOD_ID + " Item Tags";
    }
}

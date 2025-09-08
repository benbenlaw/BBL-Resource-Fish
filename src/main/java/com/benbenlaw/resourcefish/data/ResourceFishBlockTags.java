package com.benbenlaw.resourcefish.data;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ResourceFishBlockTags extends BlockTagsProvider {

    ResourceFishBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ResourceFish.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        //Pickaxe
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ResourceFishBlocks.TANK_CONTROLLER.get())
                .add(ResourceFishBlocks.CAVIAR_PROCESSOR.get())
        ;


    }

    @Override
    public @NotNull String getName() {
        return ResourceFish.MOD_ID + " Block Tags";
    }
}

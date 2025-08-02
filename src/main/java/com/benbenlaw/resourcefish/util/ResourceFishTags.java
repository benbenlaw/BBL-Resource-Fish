package com.benbenlaw.resourcefish.util;

import com.benbenlaw.core.util.CoreTags;
import com.benbenlaw.resourcefish.ResourceFish;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ResourceFishTags {

    public static class Items extends CoreTags.Items {
        public static final TagKey<Item> UPGRADES = tag(ResourceFish.MOD_ID, "upgrades");

    }
}

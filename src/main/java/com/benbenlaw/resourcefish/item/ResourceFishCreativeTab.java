package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ResourceFishCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ResourceFish.MOD_ID);

    public static final Supplier<CreativeModeTab> RESOURCE_FISH_TAB = CREATIVE_MODE_TABS.register("resource_fish", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ResourceFishItems.RESOURCE_FISH_BUCKET.get().asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.resource_fish"))
            .displayItems((parameters, output) -> {

                //Spawn Eggs
                for (ResourceType type : ResourceType.getAll()) {
                    ItemStack stack = new ItemStack(ResourceFishItems.RESOURCE_FISH_SPAWN_EGG.get());
                    stack.set(ResourceFishDataComponents.FISH_TYPE, type.getId());

                    output.accept(stack);
                }


            }).build());
}

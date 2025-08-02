package com.benbenlaw.resourcefish.screen;

import com.benbenlaw.resourcefish.ResourceFish;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ResourceFishMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, ResourceFish.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<TankControllerMenu>> TANK_CONTROLLER_MENU;


    static {

        TANK_CONTROLLER_MENU = MENUS.register("tank_controller_menu", () ->
                IMenuTypeExtension.create(TankControllerMenu::new));
    }
}

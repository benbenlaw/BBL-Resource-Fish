package com.benbenlaw.resourcefish.block.entity;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ResourceFishBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceFish.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TankControllerBlockEntity>> TANK_CONTROLLER_BLOCK_ENTITY =
            register("tank_controller_block_entity", () ->
                    BlockEntityType.Builder.of(TankControllerBlockEntity::new, ResourceFishBlocks.TANK_CONTROLLER.get()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CaviarProcessorBlockEntity>> CAVIAR_PROCESSOR_BLOCK_ENTITY =
            register("caviar_processor_block_entity", () ->
                    BlockEntityType.Builder.of(CaviarProcessorBlockEntity::new, ResourceFishBlocks.CAVIAR_PROCESSOR.get()));


    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ResourceFishBlockEntities.TANK_CONTROLLER_BLOCK_ENTITY.get(), TankControllerBlockEntity::getTankControllerItemHandlerSide);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
                ResourceFishBlockEntities.CAVIAR_PROCESSOR_BLOCK_ENTITY.get(), CaviarProcessorBlockEntity::getCaviarProcessorItemHandlerSide);
    }




    public static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(@Nonnull String name, @Nonnull Supplier<BlockEntityType.Builder<T>> initializer) {
        return BLOCK_ENTITIES.register(name, () -> initializer.get().build(null));
    }

}

package com.benbenlaw.resourcefish.block.entity;

import com.benbenlaw.core.block.entity.SyncableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TankControllerBlockEntity extends SyncableBlockEntity {

    public TankControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ResourceFishBlockEntities.TANK_CONTROLLER_BLOCK_ENTITY.get(), pos, state);
    }


    public void tick() {



    }
}

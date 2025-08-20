package com.benbenlaw.resourcefish.block;

import com.benbenlaw.resourcefish.block.entity.CaviarProcessorBlockEntity;
import com.benbenlaw.resourcefish.block.entity.ResourceFishBlockEntities;
import com.benbenlaw.resourcefish.screen.CaviarProcessorMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CaviarProcessorBlock extends BaseEntityBlock {

    public static final MapCodec<CaviarProcessorBlock> CODEC = simpleCodec(CaviarProcessorBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    public CaviarProcessorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull BlockHitResult hit) {

        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide()) {

            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof CaviarProcessorBlockEntity caviarProcessorBlockEntity) {

                if (caviarProcessorBlockEntity.onPlayerUse(player, InteractionHand.MAIN_HAND)) {
                    return InteractionResult.SUCCESS;
                } else {
                    ContainerData data = caviarProcessorBlockEntity.data;
                    player.openMenu(new SimpleMenuProvider(
                            (windowId, playerInventory, playerEntity) -> new CaviarProcessorMenu(windowId, playerInventory, blockPos, data),
                            Component.translatable("block.resourcefish.caviar_processor")), (buf -> buf.writeBlockPos(blockPos)));
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    /* FACING */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    public @NotNull BlockState rotate(BlockState blockState, Rotation pRotation) {
        return blockState.setValue(FACING, pRotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    //Block Entity methods
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState newBlockState, boolean isMoving) {
        if (blockState.getBlock() != newBlockState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof CaviarProcessorBlockEntity) {
                ((CaviarProcessorBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(blockState, level, blockPos, newBlockState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new CaviarProcessorBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ResourceFishBlockEntities.CAVIAR_PROCESSOR_BLOCK_ENTITY.get(),
                (world, blockPos, thisBlockState, blockEntity) -> blockEntity.tick());
    }
}

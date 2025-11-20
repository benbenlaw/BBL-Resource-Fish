package com.benbenlaw.resourcefish.block;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class NestBlock extends Block {
    public NestBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity be, ItemStack stack) {

        if (!level.isClientSide()) {

            int amount = level.random.nextInt(4) + 1; // Spawn between 1 and 4 fish

            for (int i = 0; i < amount; i++) {
                ResourceFishEntity fish = ResourceFishEntities.RESOURCE_FISH.get().create(level);

                double offsetX = (level.random.nextDouble() - 0.5) * 2; // Random offset between -1 and 1
                double offsetZ = (level.random.nextDouble() - 0.5) * 2; // Random offset between -1 and 1

                if (fish != null) {
                    fish.setPos(pos.getX() + offsetX, pos.getY(), pos.getZ() + offsetZ);
                    fish.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(pos), MobSpawnType.TRIGGERED, null);
                    level.addFreshEntity(fish);
                }
            }
        }
        super.playerDestroy(level, player, pos, state, be, stack);
    }
}

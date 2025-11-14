package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.Objects;
import java.util.function.Supplier;

public class ResourceFishSpawnEgg extends DeferredSpawnEggItem {
    public ResourceFishSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        BlockPos blockPos = context.getClickedPos();

        if (!level.isClientSide()) {

            ResourceFishEntity fish = ResourceFishEntities.RESOURCE_FISH.get().create(level);
            if (fish != null) {
                ResourceLocation resourceLocation = itemStack.get(ResourceFishDataComponents.FISH_TYPE.get());
                if (resourceLocation != null) {

                    fish.setResourceType(ResourceType.get(resourceLocation));
                    ResourceFishEntity.Variant variant = ResourceFishEntity.generateVariant(ResourceType.get(resourceLocation), level.getRandom());
                    fish.setVariant(variant);

                    //DEBUG: Log the variant model and pattern
                    //player.sendSystemMessage(Component.translationArg(resourceLocation));
                    //player.sendSystemMessage(Component.literal(variant.getModelBase() + " + " + variant.pattern()));

                    Direction direction = context.getClickedFace();
                    BlockPos spawnPos = blockPos.relative(direction, 1);
                    fish.setPos(spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5);

                    level.addFreshEntity(fish);
                    itemStack.shrink(1);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    level.gameEvent(fish, GameEvent.ENTITY_PLACE, blockPos);
                    return InteractionResult.CONSUME;
                }
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        }

        BlockPos blockPos = hitResult.getBlockPos();
        Direction clickedFace = hitResult.getDirection();

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResultHolder.success(itemStack);
        }

        // Check if the clicked block is water (or a liquid block)
        boolean clickedBlockIsLiquid = level.getBlockState(blockPos).getBlock() instanceof LiquidBlock;

        // Position to spawn the fish:
        // If clicking a liquid block, spawn centered in that block.
        // Otherwise, spawn centered in the block adjacent to the clicked face.
        BlockPos spawnPos = clickedBlockIsLiquid ? blockPos : blockPos.relative(clickedFace, 1);

        ResourceFishEntity fish = ResourceFishEntities.RESOURCE_FISH.get().create(level);
        if (fish == null) {
            return InteractionResultHolder.pass(itemStack);
        }

        ResourceLocation resourceLocation = itemStack.get(ResourceFishDataComponents.FISH_TYPE.get());
        if (resourceLocation == null) {
            return InteractionResultHolder.pass(itemStack);
        }

        ResourceType resourceType = ResourceType.get(resourceLocation);
        fish.setResourceType(resourceType);

        ResourceFishEntity.Variant variant = ResourceFishEntity.generateVariant(resourceType, level.getRandom());
        fish.setVariant(variant);

        fish.setPos(spawnPos.getX() + 0.5, spawnPos.getY() + 0.5, spawnPos.getZ() + 0.5);
        level.addFreshEntity(fish);

        if (!player.isCreative()) {
            itemStack.shrink(1);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        level.gameEvent(player, GameEvent.ENTITY_PLACE, spawnPos);

        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public Component getName(ItemStack stack) {
        ResourceLocation resourceType = stack.get(ResourceFishDataComponents.FISH_TYPE.get());

        if (resourceType != null) {
            String name = resourceType.getPath();

            String[] parts = name.split("_");
            for (int i = 0; i < parts.length; i++) {
                if (!parts[i].isEmpty()) {
                    parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
                }
            }
            String formattedName = String.join(" ", parts);

            // Use translatable text with formattedName as parameter
            return Component.translatable("item.resourcefish.resource_fish_spawn_egg", formattedName);
        }

        return super.getName(stack);
    }


    @Override
    public boolean spawnsEntity(ItemStack stack, EntityType<?> entityType) {
        return super.spawnsEntity(stack, EntityType.BAT);
    }

    public static ItemStack createResourceFishSpawnEgg(ResourceType resourceType) {
        ItemStack stack = new ItemStack(ResourceFishItems.RESOURCE_FISH_SPAWN_EGG.get());
        stack.set(ResourceFishDataComponents.FISH_TYPE, Objects.requireNonNull(resourceType.getId()));
        return stack;
    }

}

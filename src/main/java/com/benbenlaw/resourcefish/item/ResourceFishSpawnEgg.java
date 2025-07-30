package com.benbenlaw.resourcefish.item;

import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.util.ResourceType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
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
                    player.sendSystemMessage(Component.translationArg(resourceLocation));

                    //ResourceLocation test = ResourceLocation.parse("resourcefish:copper");

                    fish.setResourceType(ResourceType.get(resourceLocation));
                    fish.setPos(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);
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
    public Component getName(ItemStack stack) {
        ResourceLocation resourceType = stack.get(ResourceFishDataComponents.FISH_TYPE.get());

        if (resourceType != null) {
            String name = resourceType.getPath();
            String upperCaseName = name.substring(0, 1).toUpperCase() + name.substring(1);

            return Component.literal(upperCaseName + " Resource Fish Spawn Egg");
        }
        return super.getName(stack);
    }

    @Override
    public boolean spawnsEntity(ItemStack stack, EntityType<?> entityType) {
        return super.spawnsEntity(stack, EntityType.BAT);
    }

}

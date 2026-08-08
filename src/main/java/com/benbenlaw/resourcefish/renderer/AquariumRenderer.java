package com.benbenlaw.resourcefish.renderer;

import com.benbenlaw.resourcefish.block.entity.AquariumBlockEntity;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.util.ResourceType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Renders the aquarium's fish by reusing the entity's own renderer: a single client-only dummy
 * {@link ResourceFishEntity} is configured to the block's {@link ResourceType} and drawn through the
 * entity dispatcher, so all colour/pattern logic is inherited for free. The dummy is never added to
 * a level, so it never ticks - we drive its swim animation by hand from the world time.
 */
@OnlyIn(Dist.CLIENT)
public class AquariumRenderer implements BlockEntityRenderer<AquariumBlockEntity> {

    private ResourceFishEntity dummy;
    private ResourceLocation renderedTypeId;

    public AquariumRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(AquariumBlockEntity be, float partialTick, PoseStack pose, MultiBufferSource buffers,
                       int packedLight, int packedOverlay) {
        ResourceType type = be.getFishType();
        Level level = be.getLevel();
        if (type == ResourceType.NONE || level == null)
            return;

        ResourceFishEntity fish = getOrCreate(level, type);

        long time = level.getGameTime();
        // Per-block phase offset so neighbouring tanks don't swim in lockstep.
        BlockPos pos = be.getBlockPos();
        float phase = ((pos.getX() * 7 + pos.getZ() * 13) & 0xFF);
        float age = time + phase + partialTick;

        // tickCount feeds the fish's built-in sinusoidal sway (see ResourceFishRenderer#setupRotations).
        fish.tickCount = (int) (time + phase);

        pose.pushPose();
        pose.translate(0.5D, 0.45D, 0.5D);
        pose.mulPose(Axis.YP.rotationDegrees((age * 2.0F) % 360.0F));  // slow orbit so it faces around
        pose.translate(0.0D, Mth.sin(age * 0.1F) * 0.05F, 0.0D);       // gentle vertical bob
        pose.scale(0.6F, 0.6F, 0.6F);

        EntityRenderDispatcher erd = Minecraft.getInstance().getEntityRenderDispatcher();
        erd.setRenderShadow(false);
        erd.render(fish, 0.0D, 0.0D, 0.0D, 0.0F, partialTick, pose, buffers, packedLight);
        erd.setRenderShadow(true);

        pose.popPose();
    }

    private ResourceFishEntity getOrCreate(Level level, ResourceType type) {
        if (dummy == null || dummy.level() != level) {
            dummy = new ResourceFishEntity(ResourceFishEntities.RESOURCE_FISH.get(), level) {
                @Override
                public boolean isInWater() {
                    return true; // keep the fish upright; it lives in the tank
                }
            };
            renderedTypeId = null;
        }
        if (!type.getId().equals(renderedTypeId)) {
            dummy.setResourceType(type);
            renderedTypeId = type.getId();
        }
        return dummy;
    }
}

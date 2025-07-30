package com.benbenlaw.resourcefish.renderer;

import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ResourceFishRenderer extends  MobRenderer<ResourceFishEntity, EntityModel<ResourceFishEntity>> {

    private static final ResourceLocation SMALL_RESOURCE_FISH = ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "textures/entity/small_resource_fish.png");
    private static final ResourceLocation LARGE_RESOURCE_FISH = ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "textures/entity/large_resource_fish.png");

    private final TropicalFishModelA<ResourceFishEntity> modelSmall;
    private final TropicalFishModelB<ResourceFishEntity> modelLarge;

    public ResourceFishRenderer(EntityRendererProvider.Context context) {
        super(context, new TropicalFishModelA<>(context.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL_PATTERN)), 0.3F);
        this.modelSmall = new TropicalFishModelA<>(context.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL_PATTERN));
        this.modelLarge = new TropicalFishModelB<>(context.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE_PATTERN));
    }

    @Override
    public void render(ResourceFishEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        // Select model based on variant size
        if (entity.getVariant().pattern().getBase() == ResourceFishEntity.Pattern.Base.SMALL) {
            this.model = modelSmall;
        } else {
            this.model = modelLarge;
        }

        // Set color on the model if applicable
        int color = entity.getResourceColor();
        if (this.model instanceof ColorableHierarchicalModel<?> colorableModel) {
            colorableModel.setColor(color);
        }

        super.render(entity, yaw, partialTicks, poseStack, buffer, packedLight);

        // Reset color to default after render
        if (this.model instanceof ColorableHierarchicalModel<?> colorableModel) {
            colorableModel.setColor(-1);
        }
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(ResourceFishEntity entity) {
        return switch (entity.getVariant().pattern().getBase()) {
            case ResourceFishEntity.Pattern.Base.SMALL -> SMALL_RESOURCE_FISH;
            case ResourceFishEntity.Pattern.Base.LARGE -> LARGE_RESOURCE_FISH;
        };
    }
}
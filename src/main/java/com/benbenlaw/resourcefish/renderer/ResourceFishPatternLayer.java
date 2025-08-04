package com.benbenlaw.resourcefish.renderer;


import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ColorableHierarchicalModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.TropicalFishModelA;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.TropicalFish;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ResourceFishPatternLayer extends RenderLayer<ResourceFishEntity, ColorableHierarchicalModel<ResourceFishEntity>> {

    private final TropicalFishModelA<ResourceFishEntity> modelA;
    private final TropicalFishModelB<ResourceFishEntity> modelB;

    public ResourceFishPatternLayer(RenderLayerParent<ResourceFishEntity, ColorableHierarchicalModel<ResourceFishEntity>> p_174547_, EntityModelSet p_174548_) {
        super(p_174547_);
        this.modelA = new TropicalFishModelA<>(p_174548_.bakeLayer(ModelLayers.TROPICAL_FISH_SMALL_PATTERN));
        this.modelB = new TropicalFishModelB<>(p_174548_.bakeLayer(ModelLayers.TROPICAL_FISH_LARGE_PATTERN));
    }

    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int light, ResourceFishEntity entity,
                       float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float partialTick) {

        ResourceFishEntity.Variant variant = entity.getVariant();
        ResourceFishEntity.Pattern.Base base = variant.getModelBase();
        int patternColor = variant.type().getPatternColor();

        EntityModel<ResourceFishEntity> model = base == ResourceFishEntity.Pattern.Base.SMALL
                ? this.modelA
                : this.modelB;

        // Sync animations:
        this.getParentModel().copyPropertiesTo(model);

        String patternTextureName = variant.pattern().name().toLowerCase();
        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID,
                "textures/entity/patterns/" + patternTextureName + ".png");

        coloredCutoutModelCopyLayerRender(
                this.getParentModel(),
                model,
                texture,
                poseStack,
                bufferSource,
                light,
                entity,
                limbSwing,
                limbSwingAmount,
                netHeadYaw,
                headPitch,
                partialTick,
                ageInTicks,
                patternColor);
    }


}


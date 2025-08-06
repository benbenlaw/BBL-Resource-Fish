package com.benbenlaw.resourcefish.integration.fish;

import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.util.ResourceType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FishIngredientRenderer implements IIngredientRenderer<FishIngredient> {
    @Override
    public void render(GuiGraphics guiGraphics, FishIngredient ingredient) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            ResourceFishEntity fish = ingredient.getCachedEntity(mc.level);
            if (fish != null) {
                if (fish.getVariant().model() == ResourceFishEntity.Pattern.Base.LARGE) {
                    renderFish(guiGraphics, 6, 12, 28, fish);
                }
                if (fish.getVariant().model() == ResourceFishEntity.Pattern.Base.SMALL) {
                    renderFish(guiGraphics, 6, 8, 28, fish);
                }
            }
        }
    }

    private void renderFish(GuiGraphics guiGraphics, int x, int y, int size, ResourceFishEntity fish) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(x, y, 50);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-87.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(-20.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(-50.0F));
        float scale = size; // or some scale factor, e.g. 45f
        poseStack.scale(scale, scale, scale);

        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        dispatcher.overrideCameraOrientation(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F));
        dispatcher.setRenderShadow(false);

        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

        // Setup fish variant and resourceType as you do
        ResourceType resourceType = fish.getResourceType();
        fish.setResourceType(resourceType);
        if (fish.getVariant() == null) {
            fish.setVariant(ResourceFishEntity.generateVariant(resourceType, Minecraft.getInstance().level.getRandom()));
        }

        RenderSystem.runAsFancy(() -> {
            dispatcher.render(fish, 0, 0, 0, 0, 1, poseStack, buffer, 15728880);
        });
        buffer.endBatch();

        dispatcher.setRenderShadow(true);
        poseStack.popPose();
    }

    @Override
    public List<Component> getTooltip(FishIngredient ingredient, TooltipFlag flag) {

        List<Component> tooltip = new ArrayList<>();

        String name = ingredient.getFishType().getPath();

        String formattedName = Arrays.stream(name.split("_"))
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));

        Component resourceFish = Component.translatable("jei.resourcefish.resource_fish");
        tooltip.add(Component.literal(formattedName + " ").append(resourceFish));

        ResourceType resourceType = ResourceType.get(ingredient.getFishType());
        if (resourceType != null && resourceType.getBiomes() != null && !resourceType.getBiomes().isEmpty()) {

            List<String> biomes = resourceType.getBiomes();
            List<Component> biomeComponents = new ArrayList<>();

            tooltip.add(Component.translatable("jei.resourcefish.spawn_biome").withStyle(ChatFormatting.GOLD));

            for (String biome : biomes) {
                if (biome.startsWith("#")) {
                    String tagBiome = biome.substring(1).replace(":", ".");
                    Component translated = Component.translatable("tag.worldgen.biome." + tagBiome);
                    biomeComponents.add(Component.literal("- ").append(translated).withStyle(ChatFormatting.GOLD));
                } else {
                    String biomeKey = biome.substring(biome.indexOf(":") + 1);
                    String capitalizedBiome = Arrays.stream(biomeKey.split("_"))
                            .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                            .collect(Collectors.joining(" "));

                    biomeComponents.add(Component.literal("- " + capitalizedBiome).withStyle(ChatFormatting.GOLD));
                }
            }
            tooltip.addAll(biomeComponents);
        }

        return tooltip;
    }
}
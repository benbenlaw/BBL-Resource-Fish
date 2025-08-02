package com.benbenlaw.resourcefish.integration;

import com.benbenlaw.core.util.MouseUtil;
import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.ResourceFishBlocks;
import com.benbenlaw.resourcefish.entities.ResourceFishEntities;
import com.benbenlaw.resourcefish.entities.ResourceFishEntity;
import com.benbenlaw.resourcefish.item.ResourceFishDataComponents;
import com.benbenlaw.resourcefish.item.ResourceFishItems;
import com.benbenlaw.resourcefish.recipe.FishBreedingRecipe;
import com.benbenlaw.resourcefish.util.ResourceType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class FishBreedingRecipeCategory implements IRecipeCategory<FishBreedingRecipe> {

    public final static ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "fish_breeding");
    public final static ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "textures/gui/jei_fish_breeding.png");
    static final RecipeType<FishBreedingRecipe> RECIPE_TYPE = RecipeType.create(ResourceFish.MOD_ID, "fish_breeding",
            FishBreedingRecipe.class);
    private final IDrawable background;
    private final IDrawable icon;

    private ResourceFishEntity cachedEntity = null;

    @Override
    public @Nullable ResourceLocation getRegistryName(FishBreedingRecipe recipe) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(FishBreedingRecipe.Type.INSTANCE).stream()
                .filter(recipeHolder -> recipeHolder.value().equals(recipe))
                .map(RecipeHolder::id)
                .findFirst()
                .orElse(null);
    }
    public FishBreedingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 140, 37);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ResourceFishBlocks.TANK_CONTROLLER.get()));
    }

    @Override
    public RecipeType<FishBreedingRecipe> getRecipeType() {
        return JEIResourceFishPlugin.BREEDING_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.resourcefish.tank_controller");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FishBreedingRecipe recipe, IFocusGroup iFocusGroup) {

        builder.addInputSlot(43, 17).addIngredients(recipe.breedingIngredient().ingredient());

    }

    @Override
    public void draw(FishBreedingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        float scale = 50.0F;
        Font font = Minecraft.getInstance().font;
        ResourceLocation resourceA = recipe.parentIngredientA();
        ResourceLocation resourceB = recipe.parentIngredientB();
        ResourceLocation createdFish = recipe.createdFish();
        Component fishAName = getFishName(resourceA);
        Component fishBName = getFishName(resourceB);
        Component createdFishName = getFishName(createdFish);


        renderEntityInCategory(guiGraphics, 11, 25, scale, 0.5f, 0.5f, resourceA);
        if (MouseUtil.isMouseAboveArea((int) mouseX, (int) mouseY, 3, 12, 0, 0, 24, 24)) {
            guiGraphics.renderTooltip(font, fishAName, (int) mouseX, (int) mouseY);
        }

        renderEntityInCategory(guiGraphics, 83, 25, scale, 0.5f, 0.5f, resourceB);
        if (MouseUtil.isMouseAboveArea((int) mouseX, (int) mouseY, 3, 12, 0, 0, 24, 24)) {
            guiGraphics.renderTooltip(font, fishBName, (int) mouseX, (int) mouseY);
        }

        renderEntityInCategory(guiGraphics, 122, 25, scale, 0.5f, 0.5f, createdFish);
        if (MouseUtil.isMouseAboveArea((int) mouseX, (int) mouseY, 3, 12, 0, 0, 24, 24)) {
            guiGraphics.renderTooltip(font, createdFishName, (int) mouseX, (int) mouseY);
        }

    }

    private static Component getFishName(ResourceLocation resource) {
        if (resource != null) {
            String name = resource.getPath();

            String[] parts = name.split("_");
            for (int i = 0; i < parts.length; i++) {
                if (!parts[i].isEmpty()) {
                    parts[i] = parts[i].substring(0, 1).toUpperCase() + parts[i].substring(1);
                }
            }
            String formattedName = String.join(" ", parts);

            return Component.literal(formattedName + " Resource Fish");
        }
        return null;
    }

    private void renderEntityInCategory(GuiGraphics guiGraphics, int x, int y, double scale, float yaw, float pitch, ResourceLocation resource) {

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(x, y, 50.0D);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(-20.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(-40.0F));

        //Variant
        if (cachedEntity == null) {
            cachedEntity = ResourceFishEntities.RESOURCE_FISH.get().create(Minecraft.getInstance().level);
            ResourceType resourceType = ResourceType.get(resource);

            cachedEntity.setResourceType(resourceType);
            cachedEntity.setVariant(ResourceFishEntity.generateVariant(resourceType, Minecraft.getInstance().level.getRandom()));
        }
        // Apply yaw and pitch to entity rotation
        cachedEntity.setYBodyRot(yaw);
        cachedEntity.setYRot(yaw);
        cachedEntity.setYHeadRot(yaw);
        cachedEntity.setXRot(pitch);

        ResourceFishEntity.Variant variant = cachedEntity.getVariant();
        ResourceFishEntity.Pattern.Base base = variant.getModelBase();

        if (cachedEntity.getVariant().getModelBase() == ResourceFishEntity.Pattern.Base.SMALL) {
            poseStack.scale((float) scale, (float) scale, (float) scale);
        } else {
            poseStack.scale((float) scale - 10f, (float) scale - 10f, (float) scale - 10f);
            poseStack.translate(-0.15, 0, 0);
        }

        // Render entity
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityRenderDispatcher.overrideCameraOrientation(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F));
        entityRenderDispatcher.setRenderShadow(false);
        final MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(cachedEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack, bufferSource, 15728880);
        });
        bufferSource.endBatch();
        entityRenderDispatcher.setRenderShadow(true);
        poseStack.popPose();
    }


}

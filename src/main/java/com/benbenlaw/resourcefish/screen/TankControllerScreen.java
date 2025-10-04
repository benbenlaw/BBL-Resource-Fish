package com.benbenlaw.resourcefish.screen;


import com.benbenlaw.core.util.MouseUtil;
import com.benbenlaw.resourcefish.ResourceFish;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TankControllerScreen extends AbstractContainerScreen<TankControllerMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "textures/gui/tank_controller.png");

    public TankControllerScreen(TankControllerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 165;
        this.imageWidth = 175;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 67, y + 16, 176, 0, menu.getScaledProgress(), 16);
        }

    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
        renderFishInformation(guiGraphics, mouseX, mouseY);

    }

    public void renderFishInformation(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font,
                Component.literal(this.menu.data.get(2) + " / " + this.menu.data.get(3)),
                this.leftPos + 9, this.topPos + 39, 0x3F3F3F, false);

        if (MouseUtil.isMouseAboveArea(mouseX, mouseY, this.leftPos + 7, this.topPos + 34, 0, 0, 72, 18)) {

            List<Component> tooltip = new ArrayList<>();

            if (this.menu.data.get(2) > this.menu.data.get(3)) {
                tooltip.add(Component.translatable("screen.resourcefish.tank_controller.overfilled")
                        .withStyle(net.minecraft.ChatFormatting.RED));
            } else {
                tooltip.add(Component.translatable("screen.resourcefish.tank_controller.fish_types")
                        .withStyle(ChatFormatting.YELLOW));

                for (String name : this.menu.blockEntity.fishNames) {
                    tooltip.add(Component.literal("- " + name).withStyle(ChatFormatting.GRAY));
                }
            }

            guiGraphics.renderTooltip(Minecraft.getInstance().font, tooltip, Optional.empty(), mouseX, mouseY);

        }

    }


}

package com.benbenlaw.resourcefish.screen;


import com.benbenlaw.resourcefish.ResourceFish;
import com.benbenlaw.resourcefish.block.entity.CaviarProcessorBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CaviarProcessorScreen extends AbstractContainerScreen<CaviarProcessorMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(ResourceFish.MOD_ID, "textures/gui/caviar_processor.png");

    public CaviarProcessorScreen(CaviarProcessorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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

        renderProgressBars(guiGraphics, x, y);

    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderBackground(guiGraphics, mouseX, mouseY, delta);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

    }


    private void renderProgressBars(GuiGraphics guiGraphics, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        for (int slot : CaviarProcessorBlockEntity.INPUT_SLOTS) {
            int progress = menu.getScaledProgress(slot);

            if (progress > 0) {
                Point slotPosition = menu.slotPositions.get(slot);

                // Draw from bottom up: adjust both texture V and screen Y
                int fullHeight = 18; // or however tall your progress bar is
                guiGraphics.blit(
                        TEXTURE,
                        x + slotPosition.x -1,
                        y + slotPosition.y + (fullHeight - progress) - 1, // destination Y
                        176,                         // texture U
                        fullHeight - progress,       // texture V
                        18,                          // width
                        progress                     // height
                );

            }
        }
    }

}

package com.example.hud;

import com.example.config.ClientConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;

public class CoordsHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        if (!ClientConfig.showCoords) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.getDebugOverlay().showDebugScreen() || mc.player == null) return;

        // Inizia dalla cima
        int currentY = 5;
        
        // Se c'è l'FPS counter attivo, scendi di 10 pixel per fargli spazio
        if (ClientConfig.showFps) {
            currentY += 10;
        }

        BlockPos pos = mc.player.blockPosition();
        String coordsText = String.format("XYZ: %d, %d, %d", pos.getX(), pos.getY(), pos.getZ());
        
        drawContext.drawString(mc.font, coordsText, 5, currentY, 0x55FF55, true);
    }
}
package com.example.hud;

import com.example.config.ClientConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class FpsHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        if (!ClientConfig.showFps) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.getDebugOverlay().showDebugScreen()) return;

        String fpsText = "FPS: " + mc.fpsString.split(" ")[0];
        
        // Questo è sempre in cima a Y: 5
        drawContext.drawString(mc.font, fpsText, 5, 5, 0xFFFFFFFF, true);
    }
}
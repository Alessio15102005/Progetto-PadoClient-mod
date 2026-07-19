package com.example.hud;

import com.example.config.ClientConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class DayHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        if (!ClientConfig.showDayCounter) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.getDebugOverlay().showDebugScreen() || mc.level == null) return;

        // Inizia dalla cima
        int currentY = 5;
        
        // Fai spazio per gli HUD precedenti se sono attivi
        if (ClientConfig.showFps) currentY += 10;
        if (ClientConfig.showCoords) currentY += 10;

        long day = mc.level.getDayTime() / 24000L;
        
        // ORA È TRADUCIBILE: Prende la frase "Giorno: %s" e inserisce la variabile 'day'
        String dayText = Component.translatable("hud.myclient.day", day).getString();
        
        drawContext.drawString(mc.font, dayText, 5, currentY, 0xFFFFAA, true);
    }
}
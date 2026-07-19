package com.example.hud;

import com.example.config.ClientConfig;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.phys.Vec3;

public class DevConsoleOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(GuiGraphics drawContext, float tickDelta) {
        // Si attiva solo se la Dev Mode è accesa
        if (!ClientConfig.devModeActive) return;

        Minecraft mc = Minecraft.getInstance();
        // Evitiamo crash se il giocatore o il mondo non sono ancora caricati
        if (mc.player == null || mc.level == null) return;

        // Altezza aumentata a 88 per fare spazio alla nuova riga
        int width = 160;
        int height = 88; 
        int x = mc.getWindow().getGuiScaledWidth() - width - 10;
        int y = (mc.getWindow().getGuiScaledHeight() - height) / 2;

        // Sfondo scuro per il terminale dev
        drawContext.fill(x, y, x + width, y + height, 0xBB000000);
        drawContext.renderOutline(x, y, width, height, 0xFF00FF00); // Bordo verde stile "Hacker"

        // Titolo Console
        drawContext.drawCenteredString(mc.font, "--- DEV CONSOLE ---", x + (width / 2), y + 5, 0xFF00FF00);

        // 1. PING (Latenza reale con il server)
        int ping = 0;
        if (mc.getConnection() != null) {
            PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());
            if (info != null) ping = info.getLatency();
        }
        drawContext.drawString(mc.font, "Ping: " + ping + " ms", x + 5, y + 22, 0xFFFFFF, false);

        // 2. RAM ALLOCATA/USATA AL BYTE
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = allocatedMemory - freeMemory;
        
        drawContext.drawString(mc.font, "RAM Usata: " + usedMemory + " B", x + 5, y + 36, 0xFFFFAA, false);
        drawContext.drawString(mc.font, "RAM Max:   " + maxMemory + " B", x + 5, y + 48, 0xAAAAAA, false);

        // 3. COORDINATE DELLA TELECAMERA
        Vec3 camPos = mc.gameRenderer.getMainCamera().getPosition();
        String camText = String.format("Cam: %.2f, %.2f, %.2f", camPos.x, camPos.y, camPos.z);
        drawContext.drawString(mc.font, camText, x + 5, y + 60, 0x00E5FF, false);

        // 4. CONTATORE ENTITÀ ATTIVE (Ottimo per scovare cause di lag)
        int entityCount = mc.level.getEntityCount();
        drawContext.drawString(mc.font, "Entita': " + entityCount, x + 5, y + 74, 0xFFA500, false); // Colore arancione
    }
}
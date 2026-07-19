package com.example.screen; // Assicurati che corrisponda alla tua cartella

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import org.jetbrains.annotations.Nullable;

public class EndgameCrashScreen extends Screen {
    // Sostituito "fromNamespaceAndPath" con "new ResourceLocation" per compatibilità con la tua versione
    private static final ResourceLocation CRASH_LOGO_TEXTURE = new ResourceLocation("mycustomclient", "textures/gui/title/crash_logo.png");
    
    private static final String[] text = new String[]{
        "@@@@@ @@@@  @@@@   @@@  @@@@    @ @  @@   @@ ", 
        "@     @   @ @  @ @   @ @   @  @ @  @ @  @", 
        "@@@@  @@@@  @@@@ @   @ @@@@  @@@@@   @    @ ", 
        "@     @   @ @  @ @   @ @     @   @   @    @  ", 
        "@@@@@ @   @ @  @  @@@  @     @   @ @@@@ @@@@"
    };

    public EndgameCrashScreen() {
        super(Component.literal("ERR422"));
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        int w = this.width;
        int h = this.height;
        
        // Sfondo nero
        guiGraphics.fill(0, 0, w, h, -16777216);
        
        int yOffset = h / 2 - text.length * 4;

        // Disegna il testo ASCII
        for(int y = 0; y < text.length; ++y) {
            String txt = text[y];
            int xOffset = w / 2 - txt.length() * 4;

            for(int x = 0; x < txt.length(); ++x) {
                guiGraphics.drawString(this.font, txt.substring(x, x + 1), x * 8 + xOffset, y * 8 + yOffset, -7864320, false);
            }
        }

        // Disegna il logo
        int logoX = w / 2 - 160;
        guiGraphics.blit(CRASH_LOGO_TEXTURE, logoX, 0, 0.0F, 0.0F, 320, 64, 320, 64);
    }

    @Override
    public @Nullable Music getBackgroundMusic() {
        return null;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void removed() {
        // System.exit(422); rimosso per evitare che il gioco crashi letteralmente alla chiusura dello schermo
    }
}
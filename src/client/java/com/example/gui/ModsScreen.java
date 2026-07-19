package com.example.gui;

import com.example.config.ClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModsScreen extends Screen {
    private final Screen lastScreen;
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/background/background_credit.png");

    public ModsScreen(Screen lastScreen) {
        super(Component.literal("Modifiche Client"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 50;
        int spacing = 28; // Spazio leggermente aumentato per far respirare i nuovi bottoni

        // 1. Tasto FPS
        String statoFps = ClientConfig.showFps ? "ATTIVO" : "DISATTIVATO";
        this.addRenderableWidget(new CreditsScreen.PremiumButton(centerX - 100, startY, 200, 20, Component.literal("FPS Counter: " + statoFps), null, () -> {
            ClientConfig.showFps = !ClientConfig.showFps;
            this.minecraft.setScreen(this);
        }));

        // 2. Tasto Coordinate
        String statoCoords = ClientConfig.showCoords ? "ATTIVO" : "DISATTIVATO";
        this.addRenderableWidget(new CreditsScreen.PremiumButton(centerX - 100, startY + spacing, 200, 20, Component.literal("Coordinate: " + statoCoords), null, () -> {
            ClientConfig.showCoords = !ClientConfig.showCoords;
            this.minecraft.setScreen(this);
        }));

        // 3. Tasto Giorni
        String statoDay = ClientConfig.showDayCounter ? "ATTIVO" : "DISATTIVATO";
        this.addRenderableWidget(new CreditsScreen.PremiumButton(centerX - 100, startY + (spacing * 2), 200, 20, Component.literal("Contatore Giorni: " + statoDay), null, () -> {
            ClientConfig.showDayCounter = !ClientConfig.showDayCounter;
            this.minecraft.setScreen(this);
        }));

        // Bottone Indietro
        this.addRenderableWidget(new CreditsScreen.PremiumButton(centerX - 100, this.height - 40, 200, 20, Component.translatable("gui.back"), null, () -> {
            this.minecraft.setScreen(this.lastScreen);
        }));
    }

    @Override
    public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        g.blit(BACKGROUND_TEXTURE, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        this.renderBackground(g, mouseX, mouseY, delta);
        super.render(g, mouseX, mouseY, delta);
        g.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
    }
}
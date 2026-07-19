package com.example.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class UpdatesScreen extends Screen {
    private final Screen lastScreen;
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/background/background_credit.png");

    public UpdatesScreen(Screen lastScreen) {
        super(Component.translatable("gui.myclient.updates.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;

        // Bottone Indietro usando il nostro PremiumButton
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
        
        int centerX = this.width / 2;

        g.drawCenteredString(this.font, this.title, centerX, 20, 0xFFFFFFFF);
        g.drawCenteredString(this.font, Component.translatable("gui.myclient.updates.version"), centerX, 40, 0xFFD700);
        
        int listX = centerX - 140; 
        int startY = 70; 
        int spacing = 14; 

        g.drawString(this.font, Component.translatable("gui.myclient.updates.note1"), listX, startY, 0xDDDDDD);
        g.drawString(this.font, Component.translatable("gui.myclient.updates.note2"), listX, startY + spacing, 0xDDDDDD);
        g.drawString(this.font, Component.translatable("gui.myclient.updates.note3"), listX, startY + (spacing * 2), 0xDDDDDD);
        g.drawString(this.font, Component.translatable("gui.myclient.updates.note4"), listX, startY + (spacing * 3), 0xDDDDDD);
    }
}
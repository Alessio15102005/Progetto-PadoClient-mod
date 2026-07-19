package com.example.gui;

import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomWorldScreen extends SelectWorldScreen {

    private static final ResourceLocation VILLAGE_BACKGROUND = new ResourceLocation("minecraft", "textures/gui/background/background_wordl.png");
    private static final ResourceLocation CUSTOM_TITLE = new ResourceLocation("minecraft", "textures/gui/title/title.png");

    public CustomWorldScreen(Screen lastScreen) {
        super(lastScreen);
    }

    @Override
    protected void init() {
        super.init();

        int startY = this.height - 80;
        int btnWidth = 150;

        List<AbstractWidget> toRemove = new ArrayList<>();

        for (GuiEventListener child : this.children()) {
            if (child instanceof AbstractWidget widget) {
                if (widget.getMessage().equals(Component.translatable("selectWorld.select"))) {
                    toRemove.add(widget);
                }
                else if (widget.getMessage().equals(Component.translatable("selectWorld.create"))) {
                    widget.setX(20);
                    widget.setY(startY + 24);
                    widget.setWidth(btnWidth);
                }
                else if (widget.getMessage().equals(Component.translatable("gui.back"))) {
                    widget.setX(20);
                    widget.setY(startY + 48);
                    widget.setWidth(btnWidth);
                }
                else {
                    widget.visible = false;
                    widget.active = false;
                }
            }
        }

        for (AbstractWidget w : toRemove) {
            this.removeWidget(w);
        }

        // TASTI LATO SINISTRO (Tradotti)
        this.addRenderableWidget(Button.builder(Component.translatable("gui.myclient.worlds.list"), button -> {
            this.minecraft.setScreen(new SelectWorldScreen(this));
        }).bounds(20, startY, btnWidth, 20).build());

        // NUOVO TASTO LATO DESTRO (Tradotto)
        int rightX = this.width - 170;
        this.addRenderableWidget(Button.builder(Component.translatable("gui.myclient.worlds.open_folder"), button -> {
            File savesFolder = new File(this.minecraft.gameDirectory, "saves");
            Util.getPlatform().openFile(savesFolder);
        }).bounds(rightX, startY + 48, btnWidth, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 1. Sfondo
        guiGraphics.blit(VILLAGE_BACKGROUND, 0, 0, 0, 0, this.width, this.height, this.width, this.height);

        // 2. Logo Personalizzato in alto
        int logoWidth = 240;
        int logoHeight = 45;
        int logoX = (this.width - logoWidth) / 2;
        int logoY = 30;
        guiGraphics.blit(CUSTOM_TITLE, logoX, logoY, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);

        // 3. NUOVO PANNELLO INFORMATIVO (Lato Destro) - Tutto tradotto
        int panelX = this.width - 170;
        int panelY = this.height - 180;
        int panelW = 150;
        int panelH = 90;

        // Riquadro scuro di sfondo per le info
        guiGraphics.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0x88000000);
        guiGraphics.renderOutline(panelX, panelY, panelW, panelH, 0x33FFFFFF);

        // Testi interni con traduzioni
        guiGraphics.drawString(font, Component.translatable("gui.myclient.worlds.status").getString(), panelX + 10, panelY + 8, 0xFF00E5FF, false);
        guiGraphics.drawString(font, Component.translatable("gui.myclient.worlds.version").getString(), panelX + 10, panelY + 26, 0xFFFFFF, false);
        guiGraphics.drawString(font, Component.translatable("gui.myclient.worlds.engine").getString(), panelX + 10, panelY + 42, 0xAAAAAA, false);
        guiGraphics.drawString(font, Component.translatable("gui.myclient.worlds.fps_boost").getString(), panelX + 10, panelY + 58, 0x55FF55, false);
        guiGraphics.drawString(font, Component.translatable("gui.myclient.worlds.system_ready").getString(), panelX + 10, panelY + 74, 0xFFFF55, false);

        // 4. Render dei bottoni attivi
        for (GuiEventListener child : this.children()) {
            if (child instanceof AbstractWidget widget && widget.visible) {
                widget.render(guiGraphics, mouseX, mouseY, partialTick);
            }
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Vuoto per evitare sovrapposizioni grafiche
    }
}
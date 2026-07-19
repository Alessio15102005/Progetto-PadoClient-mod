package com.example.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CreditsScreen extends Screen {

    private final Screen lastScreen;
    private static final String DEV_NAME = "Alessio15102005";

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/background/background_credit.png");
    private static final ResourceLocation SETTINGS_ICON = new ResourceLocation("minecraft", "textures/gui/icon/icon.png");
    private static final ResourceLocation DEV_SKIN = new ResourceLocation("minecraft", "textures/gui/title/skin.png");
    
    private static final ResourceLocation UPDATES_ICON = new ResourceLocation("minecraft", "textures/gui/icon/icon1.png");
    private static final ResourceLocation MODS_ICON = new ResourceLocation("minecraft", "textures/gui/icon/icon2.png");
    private static final ResourceLocation COMMUNITY_ICON = new ResourceLocation("minecraft", "textures/gui/icon/icon3.png");

    public CreditsScreen(Screen lastScreen) {
        super(Component.translatable("gui.myclient.credits.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        int x = 20;       
        int y = 50;
        int w = 150;      
        int h = 26;       
        int s = 34;       

        this.addRenderableWidget(new PremiumButton(x, y, w, h, Component.translatable("gui.myclient.updates"), UPDATES_ICON, () -> {
            this.minecraft.setScreen(new UpdatesScreen(this));
        }));
        
        this.addRenderableWidget(new PremiumButton(x, y + s, w, h, Component.translatable("gui.myclient.client_mods"), MODS_ICON, () -> {
            this.minecraft.setScreen(new ModsScreen(this));
        }));
        
        this.addRenderableWidget(new PremiumButton(x, y + s * 2, w, h, Component.translatable("gui.myclient.community"), COMMUNITY_ICON, () -> {
        }));

        this.addRenderableWidget(new PremiumButton(x, y + s * 3, w, h, Component.translatable("menu.options"), SETTINGS_ICON, () -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }));

        this.addRenderableWidget(new PremiumButton(x, this.height - 40, w, h, Component.translatable("gui.back"), null, () -> {
            this.minecraft.setScreen(lastScreen);
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

        int baseX = 220; 
        int baseY = 50;
        int headSize = 40; 
        int headX = baseX;
        int headY = baseY;
        int nameX = headX + headSize + 15;
        int nameY = headY + 12;

        g.fill(baseX - 10, baseY - 10, nameX + 140, baseY + headSize + 10, 0x88000000);
        
        PlayerFaceRenderer.draw(g, DEV_SKIN, headX, headY, headSize, true, false);

        g.pose().pushPose();
        g.pose().scale(1.2f, 1.2f, 1.2f);
        g.drawString(font, DEV_NAME, (int)(nameX / 1.2f), (int)(nameY / 1.2f), 0xFFFFFF);
        g.pose().popPose();
        
        g.drawString(font, Component.translatable("gui.myclient.credits.role"), nameX, nameY + 20, 0x00E5FF, false); 

        int yText = baseY + headSize + 30;
        g.drawString(font, Component.translatable("gui.myclient.credits.architecture"), baseX, yText, 0xFFD700, false);
        g.drawString(font, Component.translatable("gui.myclient.credits.builtwith"), baseX, yText + 16, 0xAAAAAA, false);
        
        g.drawString(font, Component.translatable("gui.myclient.credits.tools"), baseX, yText + 40, 0xFFFFFF, false);
        
        g.drawString(font, "▶ Fabric API", baseX + 5, yText + 56, 0xDDDDDD);
        g.drawString(font, "▶ VS Code", baseX + 5, yText + 70, 0xDDDDDD);
        g.drawString(font, "▶ Gemini AI", baseX + 5, yText + 84, 0xDDDDDD);
        g.drawString(font, "▶ Git", baseX + 5, yText + 98, 0xDDDDDD);
    }

    public static class PremiumButton extends AbstractButton {
        private final Runnable action;
        private final ResourceLocation icon;

        public PremiumButton(int x, int y, int w, int h, Component text, ResourceLocation icon, Runnable action) {
            super(x, y, w, h, text);
            this.action = action;
            this.icon = icon;
        }

        @Override
        public void onPress() { action.run(); }

        @Override
        public void renderWidget(GuiGraphics g, int mx, int my, float delta) {
            boolean hovered = isHoveredOrFocused();

            int bg = hovered ? 0xEE1A1A1A : 0xAA000000; 
            int accentColor = 0xFF00E5FF; 
            int textColor = this.active ? (hovered ? 0xFFFFFFFF : 0xFFAAAAAA) : 0x555555;
            
            int offset = hovered ? 5 : 0; 

            // Sfondo ben visibile
            g.fill(getX(), getY(), getX() + width, getY() + height, bg);

            if (hovered) {
                // Animazione di selezione (Hover)
                g.fill(getX(), getY(), getX() + 3, getY() + height, accentColor);
                g.renderOutline(getX(), getY(), width, height, 0x5500E5FF);
            } else {
                // Bordo sottile permanente quando a riposo
                g.renderOutline(getX(), getY(), width, height, 0x33FFFFFF);
            }

            if (this.icon != null) {
                g.blit(this.icon, this.getX() + 10 + offset, this.getY() + (this.height - 16) / 2, 0, 0, 16, 16, 16, 16);
                g.drawString(Minecraft.getInstance().font, this.getMessage(), this.getX() + 34 + offset, this.getY() + (this.height - 8) / 2, textColor, false);
            } else {
                g.drawString(Minecraft.getInstance().font, this.getMessage(), this.getX() + 15 + offset, this.getY() + (this.height - 8) / 2, textColor, false);
            }
        }

        @Override
        protected void updateWidgetNarration(net.minecraft.client.gui.narration.NarrationElementOutput n) {}
    }
}
package com.example.mixin;

import com.example.gui.CustomWorldScreen; 
import com.example.gui.CreditsScreen; 
import com.example.gui.CosmeticsScreen; // La nuova schermata che creeremo
import net.minecraft.Util; 
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    private static final ResourceLocation PANDORA_BACKGROUND = new ResourceLocation("minecraft", "textures/gui/background/background.png");
    private static final ResourceLocation CLIENT_LOGO = new ResourceLocation("minecraft", "textures/gui/title/padoclient.png");
    private static final ResourceLocation MONDI_ICON = new ResourceLocation("minecraft", "textures/gui/icon/icon6.png");
    private static final ResourceLocation CREDITS_ICON = new ResourceLocation("minecraft", "textures/gui/icon/icon4.png");
    private static final ResourceLocation DISCORD_ICON = new ResourceLocation("minecraft", "textures/gui/icon/discord.png");
    
    // NUOVA ICONA PER I COSMETICI (Es. un piccolo mantello o una maglietta)
    private static final ResourceLocation COSMETICS_ICON = new ResourceLocation("minecraft", "textures/gui/icon/icon5.png");

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void renderCustomBackground(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if ((Object) this instanceof TitleScreen) {
            int width = guiGraphics.guiWidth();
            int height = guiGraphics.guiHeight();

            guiGraphics.blit(PANDORA_BACKGROUND, 0, 0, 0, 0, width, height, width, height);

            int logoWidth = 240;  
            int logoHeight = 40;  
            int logoX = (width / 2) - (logoWidth / 2);
            int logoY = (height / 4) + 10;            

            guiGraphics.blit(CLIENT_LOGO, logoX, logoY, 0, 0, logoWidth, logoHeight, logoWidth, logoHeight);

            ci.cancel(); 
        }
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void buildCustomLayout(CallbackInfo ci) {
        this.clearWidgets();

        int centerX = this.width / 2;
        int baseY = this.height / 4 + 75;

        // RIGA 1: Discord, Multiplayer, Mondi
        this.addRenderableWidget(Button.builder(Component.empty(), button -> {
            Util.getPlatform().openUri("https://discord.gg/TUO_LINK_QUI");
        }).bounds(centerX - 124, baseY, 20, 20).build());
        
        this.addRenderableWidget(Button.builder(Component.translatable("menu.multiplayer"), button -> {
            this.minecraft.setScreen(new JoinMultiplayerScreen(this));
        }).bounds(centerX - 100, baseY, 200, 20).build());
        
        this.addRenderableWidget(Button.builder(Component.empty(), button -> {
            this.minecraft.setScreen(new CustomWorldScreen(this)); 
        }).bounds(centerX + 104, baseY, 20, 20).build());

        // RIGA 2: COSMETICI, Opzioni, Esci e Crediti
        int row2Y = baseY + 24;
        
        // IL NUOVO BOTTONE COSMETICI (Sostituisce quello vuoto)
        this.addRenderableWidget(Button.builder(Component.empty(), button -> {
            this.minecraft.setScreen(new CosmeticsScreen(this)); // Apre il menu dei mantelli
        }).bounds(centerX - 124, row2Y, 20, 20).build());
        
        this.addRenderableWidget(Button.builder(Component.translatable("menu.options"), button -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }).bounds(centerX - 100, row2Y, 98, 20).build());
        
        this.addRenderableWidget(Button.builder(Component.translatable("menu.quit"), button -> {
            this.minecraft.stop();
        }).bounds(centerX + 2, row2Y, 98, 20).build());

        this.addRenderableWidget(Button.builder(Component.empty(), button -> {
            this.minecraft.setScreen(new CreditsScreen(this));
        }).bounds(centerX + 104, row2Y, 20, 20).build());
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void renderIcons(net.minecraft.client.gui.GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        int centerX = this.width / 2;
        int baseY = this.height / 4 + 75; 
        int row2Y = baseY + 24;

        // Icona Discord (Fila 1 SX)
        guiGraphics.blit(DISCORD_ICON, centerX - 124 + 2, baseY + 2, 0, 0, 16, 16, 16, 16);
        
        // Icona Mondi (Fila 1 DX)
        guiGraphics.blit(MONDI_ICON, centerX + 104 + 2, baseY + 2, 0, 0, 16, 16, 16, 16);
        
        // Icona Cosmetici/Mantelli (Fila 2 SX)
        guiGraphics.blit(COSMETICS_ICON, centerX - 124 + 2, row2Y + 2, 0, 0, 16, 16, 16, 16);

        // Icona Crediti (Fila 2 DX)
        guiGraphics.blit(CREDITS_ICON, centerX + 104 + 2, row2Y + 2, 0, 0, 16, 16, 16, 16);
    }
}
package com.example.mixin.client;

import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin {
    private static final ResourceLocation PAGMAN_TEXTURE = new ResourceLocation("mycustomclient", "textures/gui/pagman.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void renderPagmanLoadingIcon(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        LevelLoadingScreen screen = (LevelLoadingScreen) (Object) this;
        
        // Troviamo il centro perfetto dello schermo
        int centerX = screen.width / 2;
        int centerY = screen.height / 2;

        // Calcoliamo l'angolo di rotazione basandoci sul tempo di sistema per farlo girare in modo fluido
        float angle = (System.currentTimeMillis() / 4) % 360;

        // Usiamo la matrice delle pose di Minecraft per applicare la rotazione grafica
        guiGraphics.pose().pushPose();
        
        // Ci spostiamo nel punto esatto sopra la percentuale di caricamento
        guiGraphics.pose().translate(centerX, centerY - 60, 0);
        // Applichiamo la rotazione sull'asse Z (rotazione a cerchio)
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(angle));
        
        // Disegnamo l'immagine centrata (dimensione 40x40 pixel)
        guiGraphics.blit(PAGMAN_TEXTURE, -20, -20, 0, 0, 40, 40, 40, 40);
        
        guiGraphics.pose().popPose();
    }
}
package com.example.mixin;

import com.example.cosmetic.CosmeticRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
    private void onGetSkin(CallbackInfoReturnable<PlayerSkin> cir) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

        // Controlliamo che sia il nostro giocatore e che il mantello nel Registro abbia una texture valida
        if (player == Minecraft.getInstance().player && 
            CosmeticRegistry.currentCape != null && 
            CosmeticRegistry.currentCape.getTexture() != null) {
            
            PlayerSkin originalSkin = cir.getReturnValue();
            
            // Creiamo una nuova skin copiando l'originale ma sostituendo il mantello con quello del Registro
            PlayerSkin newSkin = new PlayerSkin(
                    originalSkin.texture(),
                    originalSkin.textureUrl(),
                    CosmeticRegistry.currentCape.getTexture(), // <--- IL NOSTRO MANTELLO INIETTATO DAL REGISTRO
                    originalSkin.elytraTexture(),
                    originalSkin.model(),
                    originalSkin.secure()
            );
            
            cir.setReturnValue(newSkin);
        }
    }
}
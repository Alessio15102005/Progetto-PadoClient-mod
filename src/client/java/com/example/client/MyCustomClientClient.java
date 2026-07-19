package com.example.client;

import com.example.cosmetic.CosmeticRegistry;
import com.example.config.ClientConfig;
import com.example.hud.CoordsHudOverlay;
import com.example.hud.DayHudOverlay;
import com.example.hud.DevConsoleOverlay;
import com.example.hud.FpsHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class MyCustomClientClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Inizializza il Registro dei Cosmetici
        CosmeticRegistry.init();

        // Registra HUD
        HudRenderCallback.EVENT.register(new FpsHudOverlay());
        HudRenderCallback.EVENT.register(new CoordsHudOverlay());
        HudRenderCallback.EVENT.register(new DayHudOverlay());
        HudRenderCallback.EVENT.register(new DevConsoleOverlay());

        // Registra Tasto F12
        KeyMapping devKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.myclient.devmode",
            GLFW.GLFW_KEY_F12,
            "category.myclient"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (devKey.consumeClick()) {
                ClientConfig.devModeActive = !ClientConfig.devModeActive;
            }
        });
    }
}
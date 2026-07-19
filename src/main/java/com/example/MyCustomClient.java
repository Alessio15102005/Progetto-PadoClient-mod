package com.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCustomClient implements ModInitializer {
    public static final String MOD_ID = "mycustomclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // Qui metti solo logica server/comune (es. registrazione oggetti, blocchi)
        LOGGER.info("MyCustomClient caricato correttamente (Modo Comune)!");
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
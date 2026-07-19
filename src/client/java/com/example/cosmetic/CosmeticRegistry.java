package com.example.cosmetic;

import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.List;

public class CosmeticRegistry {
    public static final List<Cape> CAPES = new ArrayList<>();
    public static Cape currentCape = null;

    public static void init() {
        CAPES.clear(); 
        
        // --- REGISTRAZIONE MANTELLI ---
        // Assicurati che ogni file .png esista nel percorso specificato
        registerCape("none", "Nessun Mantello", null);
        registerCape("mojang", "Mojang Classico", new ResourceLocation("mycustomclient", "textures/capes/mojang.png"));
        registerCape("zerotwo", "ZeroTwo Custom", new ResourceLocation("mycustomclient", "textures/capes/zerotwo.png"));
       
        registerCape("enderdragon", "Ender Dragon", new ResourceLocation("mycustomclient", "textures/capes/enderdragon.png"));
        registerCape("twitch", "twitch", new ResourceLocation("mycustomclient", "textures/capes/twitch.png"));
        registerCape("dev", "Svilupattore/Dev", new ResourceLocation("mycustomclient", "textures/capes/dev.png"));



    
        
        // --- LOGICA DI CARICAMENTO PERSISTENTE ---
        String savedId = CosmeticConfig.load();
        boolean found = false;

        if (savedId != null) {
            for (Cape cape : CAPES) {
                if (cape.getId().equals(savedId)) {
                    currentCape = cape;
                    found = true;
                    break;
                }
            }
        }

        // Se non trova il salvataggio o l'ID è errato, imposta il default (Nessuno)
        if (!found) {
            currentCape = CAPES.get(0);
        }
    }

    private static void registerCape(String id, String name, ResourceLocation texture) {
        CAPES.add(new Cape(id, name, texture));
    }

    public static class Cape {
        private final String id;
        private final String name;
        private final ResourceLocation texture;

        public Cape(String id, String name, ResourceLocation texture) {
            this.id = id;
            this.name = name;
            this.texture = texture;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public ResourceLocation getTexture() { return texture; }
    }
}
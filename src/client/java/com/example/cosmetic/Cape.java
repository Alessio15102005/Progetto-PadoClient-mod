package com.example.cosmetic;

import net.minecraft.resources.ResourceLocation;

public class Cape {
    private final String id;
    private final String name;
    private final ResourceLocation texture;

    public Cape(String id, String name, ResourceLocation texture) {
        this.id = id;
        this.name = name;
        this.texture = texture;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
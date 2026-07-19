package com.example.cosmetic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import java.io.*;

public class CosmeticConfig {
    private static final File CONFIG_FILE = new File(Minecraft.getInstance().gameDirectory, "config/mantello_scelto.json");

    public static void save(String capeId) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("capeId", capeId);
            
            if (!CONFIG_FILE.getParentFile().exists()) CONFIG_FILE.getParentFile().mkdirs();
            
            FileWriter writer = new FileWriter(CONFIG_FILE);
            writer.write(json.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String load() {
        if (!CONFIG_FILE.exists()) return null;
        try {
            FileReader reader = new FileReader(CONFIG_FILE);
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            return json.get("capeId").getAsString();
        } catch (Exception e) {
            return null;
        }
    }
}
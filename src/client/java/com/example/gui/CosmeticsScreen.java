package com.example.gui;

import com.example.cosmetic.CosmeticRegistry;
import com.example.cosmetic.CosmeticConfig;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Quaternionf;

public class CosmeticsScreen extends Screen {
    private final Screen parent;
    private int previewIndex = 0;
    private ModelPart capeModel;
    private static final int MODEL_SCALE = 160;
    
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/background/background_cosmetics.png");
    private static final ResourceLocation TITLE_TEXTURE = new ResourceLocation("minecraft", "textures/gui/title/cosmetics.png");

    public CosmeticsScreen(Screen parent) {
        super(Component.translatable("gui.myclient.cosmetics.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("cape", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-5.0F, 0.0F, -0.5F, 10.0F, 16.0F, 1.0F), PartPose.ZERO);
        
        LayerDefinition layerdefinition = LayerDefinition.create(meshdefinition, 64, 32);
        this.capeModel = layerdefinition.bakeRoot().getChild("cape");

        this.addRenderableWidget(Button.builder(Component.translatable("gui.myclient.cosmetics.previous"), b -> {
            if (previewIndex > 0) previewIndex--;
        }).bounds(centerX - 60, centerY - 25, 120, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("gui.myclient.cosmetics.next"), b -> {
            if (previewIndex < CosmeticRegistry.CAPES.size() - 1) previewIndex++;
        }).bounds(centerX - 60, centerY + 5, 120, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("gui.myclient.cosmetics.equip"), b -> {
            CosmeticRegistry.currentCape = CosmeticRegistry.CAPES.get(previewIndex);
            CosmeticConfig.save(CosmeticRegistry.currentCape.getId()); 
        }).bounds(centerX - 60, centerY + 35, 120, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("gui.myclient.cosmetics.back"), b -> this.minecraft.setScreen(this.parent))
                .bounds(centerX - 60, centerY + 65, 120, 20).build());
    }

    @Override
    public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        g.blit(BACKGROUND_TEXTURE, 0, 0, 0, 0, this.width, this.height, this.width, this.height);
        g.fill(0, 0, this.width, this.height, 0x88000000);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int leftPanelX = this.width / 4;

        boolean isEquipped = (CosmeticRegistry.CAPES.get(previewIndex) == CosmeticRegistry.currentCape);

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        g.blit(TITLE_TEXTURE, centerX - 100, 15, 0, 0, 200, 40, 200, 40);
        
        int nameColor = isEquipped ? 0x55FF55 : 0xFFFFFF;
        String nameText = CosmeticRegistry.CAPES.get(previewIndex).getName() + (isEquipped ? Component.translatable("gui.myclient.cosmetics.active").getString() : "");
        g.drawCenteredString(this.font, nameText, centerX, centerY - 45, nameColor);

        g.fill(leftPanelX - 80, centerY - 110, leftPanelX + 80, centerY + 110, 0x66000000);
        g.drawCenteredString(this.font, Component.translatable("gui.myclient.cosmetics.preview"), leftPanelX, centerY - 100, 0xAAAAAA);

        if (previewIndex > 0) {
            renderCape3D(g, leftPanelX, centerY - 70, MODEL_SCALE, mouseX);
        } else {
            g.drawCenteredString(this.font, Component.translatable("gui.myclient.cosmetics.none"), leftPanelX, centerY, 0xFF5555);
        }
    }

    private void renderCape3D(GuiGraphics g, int x, int y, int scale, int mouseX) {
        ResourceLocation capeTexture = CosmeticRegistry.CAPES.get(previewIndex).getTexture();
        if (capeTexture == null || this.capeModel == null) return;
        
        Minecraft mc = Minecraft.getInstance();
        PoseStack poseStack = g.pose();
        poseStack.pushPose();
        
        poseStack.translate((float) x, (float) y, 1050.0F);
        poseStack.scale(1.0F, 1.0F, -1.0F);
        poseStack.translate(0.0, 0.0, 1000.0);
        poseStack.scale((float) scale, (float) scale, (float) scale);
        
        float rotationY = (float) mouseX / this.width * 360.0F - 180.0F;
        poseStack.mulPose(new Quaternionf().rotateY((float) Math.toRadians(rotationY)));
        
        Lighting.setupForFlatItems();
        
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        this.capeModel.render(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(capeTexture)), 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        bufferSource.endBatch();
        
        Lighting.setupFor3DItems();
        
        poseStack.popPose();
    }
}
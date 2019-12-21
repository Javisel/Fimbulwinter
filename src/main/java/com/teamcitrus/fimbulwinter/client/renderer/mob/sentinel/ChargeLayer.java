package com.teamcitrus.fimbulwinter.client.renderer.mob.sentinel;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.teamcitrus.fimbulwinter.client.renderer.model.SentinelModel;
import com.teamcitrus.fimbulwinter.common.objects.entities.FrozenSpider;
import com.teamcitrus.fimbulwinter.common.objects.entities.Sentinel;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.util.ResourceLocation;

public class ChargeLayer<T extends Sentinel, M extends SentinelModel<T>> extends LayerRenderer<T, M> {
    private static final ResourceLocation CHARGE = new ResourceLocation(Fimbulwinter.MODID,"textures/entity/sentinel/sentinel_charge.png");


    public ChargeLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(T entityIn, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {

        float colormultiplier = entityIn.getCHARGEUP() / 10;
        if (entityIn.getSTATE()!=1) return;
        this.bindTexture(CHARGE);
        GlStateManager.enableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        if (entityIn.isInvisible()) {
            GlStateManager.depthMask(false);
        } else {
            GlStateManager.depthMask(true);
        }

        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) j, (float) k);
        GlStateManager.color4f(colormultiplier, colormultiplier, colormultiplier, 1.0F);
        GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
        gamerenderer.setupFogColor(true);
        this.getEntityModel().render(entityIn, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);

        gamerenderer.setupFogColor(false);
        i = entityIn.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, (float) j, (float) k);
        this.func_215334_a(entityIn);

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

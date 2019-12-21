package com.teamcitrus.fimbulwinter.client.renderer.mob.sentinel;

import com.mojang.blaze3d.platform.GlStateManager;
import com.teamcitrus.fimbulwinter.client.renderer.model.SentinelModel;
import com.teamcitrus.fimbulwinter.common.objects.entities.Sentinel;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderSentinel extends MobRenderer<Sentinel,SentinelModel<Sentinel>> {
    ResourceLocation BASE = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/sentinel/sentinel.png");
    ResourceLocation CHARGE = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/sentinel/sentinel_charge.png");
    ResourceLocation RAGE = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/sentinel/sentinel_rage.png");

    public RenderSentinel(EntityRendererManager p_i50961_1_) {
        super(p_i50961_1_, new SentinelModel(), 0.7F);
       // this.addLayer(new ChargeLayer<>(this));
    }



    @Nullable


    public static class RenderFactory implements IRenderFactory<Sentinel> {
        @Override
        public EntityRenderer<? super Sentinel> createRenderFor(EntityRendererManager manager) {
            final RenderSentinel renderSentinel = new RenderSentinel(manager);
            return renderSentinel;
        }

    }

    @Override
    public void doRender(Sentinel entity, double x, double y, double z, float entityYaw, float partialTicks) {
        SentinelModel<Sentinel> sentinelSentinelModel = this.getEntityModel();




        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(Sentinel entity) {

        if (entity.getSTATE()<=1) {
            return  CHARGE;
        }
        else if (entity.getSTATE()==2) {
            return  BASE;
        }

        else {

            return  RAGE;
        }


    }

    @Override
    protected void applyRotations(Sentinel entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
        if (!((double)entityLiving.limbSwingAmount < 0.01D)) {
            float f = 13.0F;
            float f1 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            GlStateManager.rotatef(6.5F * f2, 0.0F, 0.0F, 1.0F);
        }    }


}

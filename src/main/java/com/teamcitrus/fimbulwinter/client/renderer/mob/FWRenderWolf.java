package com.teamcitrus.fimbulwinter.client.renderer.mob;

import com.mojang.blaze3d.platform.GlStateManager;
import com.teamcitrus.fimbulwinter.common.objects.entities.FWWolf;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FWRenderWolf extends WolfRenderer {

    ResourceLocation UNTAMED_HUSKY = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/untamed_husky.png");
    ResourceLocation ANGRY_HUSKY = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/angry_husky.png");
    ResourceLocation TAMED_HUSKY = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/tamed_husky.png");

    public FWRenderWolf(EntityRendererManager p_i47187_1_) {
        super(p_i47187_1_);
    }

    @Override
    protected ResourceLocation getEntityTexture(WolfEntity p_110775_1_) {
       if (p_110775_1_ instanceof FWWolf) {



           FWWolf wolf = (FWWolf) p_110775_1_;

           if (wolf.getTextureType()==1) {

               if (!wolf.isTamed()) {
                   if (wolf.isAngry()) {

                       return ANGRY_HUSKY;
                   }
                    return UNTAMED_HUSKY;

               }

               return  TAMED_HUSKY;
           }





       }
       return  super.getEntityTexture(p_110775_1_);
    }
}
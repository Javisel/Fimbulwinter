package com.teamcitrus.fimbulwinter.client.renderer.model;

import com.teamcitrus.fimbulwinter.common.objects.entities.FrozenZombie;
import net.minecraft.client.renderer.entity.model.AbstractZombieModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrozenZombieModel extends BipedModel<FrozenZombie>{

    public FrozenZombieModel() {
        this(0.0F, false);
    }

    @Override
    public void setRotationAngles(FrozenZombie entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
            super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            boolean flag = this.func_212850_a_(entityIn);
            float f = MathHelper.sin(this.swingProgress * (float)Math.PI);
            float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float)Math.PI);
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
            this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
            float f2 = -(float)Math.PI / (flag ? 1.5F : 2.25F);
            this.bipedRightArm.rotateAngleX = f2;
            this.bipedLeftArm.rotateAngleX = f2;
            this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
            this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
            this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        }


    public FrozenZombieModel(float modelSize, boolean p_i1168_2_) {
        super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    protected FrozenZombieModel(float p_i48914_1_, float p_i48914_2_, int p_i48914_3_, int p_i48914_4_) {
        super(p_i48914_1_, p_i48914_2_, p_i48914_3_, p_i48914_4_);
    }


    @Override
    public void func_217142_c(float p_217142_1_) {

    }



    public boolean func_212850_a_(FrozenZombie p_212850_1_) {
        return p_212850_1_.isAggressive();
    }
}

package com.teamcitrus.fimbulwinter.client.renderer.model;

import com.teamcitrus.fimbulwinter.common.objects.entities.Sentinel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SentinelModel<T extends Sentinel> extends EntityModel<T> {
    public final RendererModel rightarm;
    private final RendererModel body;
    private final RendererModel head;
    private final RendererModel leftarm;

    public SentinelModel() {
        this(0.0F);
    }

    public SentinelModel(float p_i1161_1_) {
        this(p_i1161_1_, -7.0F);
    }

    public SentinelModel(float p_i46362_1_, float p_i46362_2_) {
        textureWidth = 128;
        textureHeight = 128;

        head = new RendererModel(this);
        head.setRotationPoint(-1.5F, -22.75F, -4.0F);
        head.cubeList.add(new ModelBox(head, 48, 32, -5.0F, -4.0F, -4.0F, 10, 8, 8, 0.0F, false));

        body = new RendererModel(this);
        body.setRotationPoint(-1.3F, -3.95F, -3.3F);
        body.cubeList.add(new ModelBox(body, 0, 0, -9.95F, -14.8F, -4.7F, 20, 10, 12, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 22, -8.95F, -4.8F, -3.7F, 18, 8, 10, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 40, -7.95F, 3.2F, -2.7F, 16, 8, 8, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 68, 48, -2.95F, 11.2F, -1.7F, 6, 4, 6, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 68, 68, -6.2F, -14.8F, -5.7F, 12, 10, 1, 0.0F, false));

        leftarm = new RendererModel(this);
        leftarm.setRotationPoint(-17.9167F, -16.1667F, -2.0F);
        leftarm.cubeList.add(new ModelBox(leftarm, 0, 56, -2.0833F, -1.8333F, -3.0F, 4, 30, 6, 0.0F, false));
        leftarm.cubeList.add(new ModelBox(leftarm, 0, 22, -3.0833F, 26.1667F, -2.0F, 1, 4, 4, 0.0F, false));
        leftarm.cubeList.add(new ModelBox(leftarm, 20, 56, -3.0833F, 2.1667F, -4.0F, 1, 21, 8, 0.0F, false));

        rightarm = new RendererModel(this);
        rightarm.setRotationPoint(15.9167F, -16.1667F, -2.0F);
        rightarm.cubeList.add(new ModelBox(rightarm, 48, 48, -2.1667F, -1.8333F, -3.0F, 4, 30, 6, 0.0F, false));
        rightarm.cubeList.add(new ModelBox(rightarm, 0, 0, 1.8333F, 26.1667F, -2.0F, 1, 4, 4, 0.0F, false));
        rightarm.cubeList.add(new ModelBox(rightarm, 64, 0, 1.8333F, 2.1667F, -4.0F, 1, 21, 8, 0.0F, false));
    }

    public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.head.render(scale);
        this.body.render(scale);

        this.leftarm.render(scale);
        this.rightarm.render(scale);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {


    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        int i = entityIn.getAttackTimer();

        if (i > 0) {
            resetRotations();

            this.leftarm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTick, 10.0F);
            this.rightarm.rotateAngleX = -2.0F + 1.5F * this.triangleWave((float) i - partialTick, 10.0F);

        } else {

            if (entityIn.getBattleStage() == 1) {
                this.leftarm.rotateAngleZ = 1.5708F;
                this.rightarm.rotateAngleZ = -1.5708F;
                this.body.rotateAngleY = entityIn.getPercentSpin();
                this.head.rotateAngleY = entityIn.getPercentSpin();
                this.leftarm.rotateAngleX = entityIn.getPercentSpin();
                this.rightarm.rotateAngleX = entityIn.getPercentSpin() * -1;


            } else if (entityIn.getBattleStage() == 2) {

                resetRotations();

                this.leftarm.rotateAngleX = (float) (1.5 * entityIn.getPercentPraise());
                this.rightarm.rotateAngleX = (float) (1.5 * entityIn.getPercentPraise());


            } else {

                resetRotations();

            }

        }


    }

    public void resetRotations() {
        this.leftarm.rotateAngleZ = 0;
        this.rightarm.rotateAngleZ = 0;
        this.body.rotateAngleY = 0;
        this.head.rotateAngleY = 0;
        this.leftarm.rotateAngleX = 0;
        this.rightarm.rotateAngleX = 0;
        this.leftarm.rotateAngleY = 0;
        this.rightarm.rotateAngleY = 0;
    }


    private float triangleWave(float p_78172_1_, float p_78172_2_) {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }

    public RendererModel func_205071_a() {
        return this.leftarm;
    }
}
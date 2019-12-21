package com.teamcitrus.fimbulwinter.client.renderer.mob;

import com.teamcitrus.fimbulwinter.client.renderer.model.FrozenZombieModel;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFrozenZombie extends BipedRenderer {

    ResourceLocation FZTEXTURE = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/frozen_zombie.png");

    public RenderFrozenZombie(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FrozenZombieModel(),0.5F);
    }


    public static class RenderFactory implements IRenderFactory<Entity> {
        @Override
        public EntityRenderer<? super Entity> createRenderFor(EntityRendererManager manager) {
            final RenderFrozenZombie renderFrozenZombie = new RenderFrozenZombie(manager);
            return renderFrozenZombie;
        }

    }

    @Override
    protected ResourceLocation getEntityTexture(MobEntity entity) {
        return FZTEXTURE;
    }
}

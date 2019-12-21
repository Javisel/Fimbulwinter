package com.teamcitrus.fimbulwinter.client.renderer.mob;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFrozenCreeper  extends CreeperRenderer {
    ResourceLocation FZTEXTURE = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/frozen_creeper.png");

    @Override
    protected ResourceLocation getEntityTexture(CreeperEntity entity) {
        return FZTEXTURE;
    }

    public RenderFrozenCreeper(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }
    public static class RenderFactory implements IRenderFactory<CreeperEntity> {
        @Override
        public EntityRenderer<? super CreeperEntity> createRenderFor(EntityRendererManager manager) {
            final RenderFrozenCreeper renderFrozenZombie = new RenderFrozenCreeper(manager);
            return renderFrozenZombie;
        }

    }

}

package com.teamcitrus.fimbulwinter.client.renderer.mob.frozen_spider;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFrozenSpider extends SpiderRenderer {

    ResourceLocation FZTEXTURE = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/frozen_spider/frozen_spider.png");


    @Override
    protected ResourceLocation getEntityTexture(SpiderEntity entity) {

        return FZTEXTURE;
    }

    public RenderFrozenSpider(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.addLayer(new FrozenSpiderAbdomenLayer(this));
        this.addLayer(new FrozenSpiderFangLayer(this));

    }

    public static class RenderFactory implements IRenderFactory<Entity> {
        @Override
        public EntityRenderer<? super Entity> createRenderFor(EntityRendererManager manager) {
            final RenderFrozenSpider renderFrozenZombie = new RenderFrozenSpider(manager);
            return renderFrozenZombie;
        }

    }

}

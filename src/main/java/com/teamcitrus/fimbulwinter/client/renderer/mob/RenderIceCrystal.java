package com.teamcitrus.fimbulwinter.client.renderer.mob;

import com.teamcitrus.fimbulwinter.client.renderer.model.IceCrystalModel;
import com.teamcitrus.fimbulwinter.common.objects.entities.IceCrystal;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;
@OnlyIn(Dist.CLIENT)

public class RenderIceCrystal extends MobRenderer<IceCrystal, IceCrystalModel> {

    ResourceLocation FZTEXTURE = new ResourceLocation(Fimbulwinter.MODID, "textures/entity/ice_crystal.png");

    public RenderIceCrystal(EntityRendererManager renderManagerIn) {
        super(renderManagerIn,new IceCrystalModel(),1.0F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(IceCrystal entity) {
        return FZTEXTURE;
    }


    public static class RenderFactory implements IRenderFactory<IceCrystal> {
        @Override
        public EntityRenderer<? super IceCrystal> createRenderFor(EntityRendererManager manager) {
            final RenderIceCrystal RenderIceCrystal = new RenderIceCrystal(manager);
            return RenderIceCrystal;
        }

    }

}

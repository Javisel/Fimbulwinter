package com.teamcitrus.fimbulwinter.client.renderer;

import com.teamcitrus.fimbulwinter.client.renderer.mob.RenderFrozenCreeper;
import com.teamcitrus.fimbulwinter.client.renderer.mob.RenderIceCrystal;
import com.teamcitrus.fimbulwinter.client.renderer.mob.sentinel.RenderSentinel;
import com.teamcitrus.fimbulwinter.common.objects.entities.*;
import com.teamcitrus.fimbulwinter.client.renderer.mob.frozen_spider.RenderFrozenSpider;
import com.teamcitrus.fimbulwinter.client.renderer.mob.RenderFrozenZombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderRegistration {


    public static void registryEntityRenders() {
        RenderingRegistry.registerEntityRenderingHandler(FrozenZombie.class, new RenderFrozenZombie.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(FrozenCreeper.class, new RenderFrozenCreeper.RenderFactory());

        RenderingRegistry.registerEntityRenderingHandler(FrozenSpider.class, new RenderFrozenSpider.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(Sentinel.class,new RenderSentinel.RenderFactory());
        RenderingRegistry.registerEntityRenderingHandler(IceCrystal.class, new RenderIceCrystal.RenderFactory());

    }


}

package com.teamcitrus.fimbulwinter.common.registration;

import com.teamcitrus.fimbulwinter.common.objects.effect.Frostbite;
import com.teamcitrus.fimbulwinter.common.objects.effect.MobEffect;
import com.teamcitrus.fimbulwinter.common.objects.effect.PhoenixFire;
import com.teamcitrus.fimbulwinter.common.objects.effect.Vulnerability;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Fimbulwinter.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EffectRegistration {

    public static MobEffect PheonixFlame = null;
    public static MobEffect VULNERABILITY = null;
    public static MobEffect FROSTBITE = null;
    @SubscribeEvent
    public static void registerEffect(final RegistryEvent.Register<Effect> event) {
        event.getRegistry().registerAll
                (
                        PheonixFlame = new PhoenixFire(),
                        VULNERABILITY = new Vulnerability(),
                        FROSTBITE = new Frostbite()

                );
    }
}

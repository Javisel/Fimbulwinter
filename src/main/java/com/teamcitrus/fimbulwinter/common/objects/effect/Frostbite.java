package com.teamcitrus.fimbulwinter.common.objects.effect;

import com.teamcitrus.fimbulwinter.common.objects.damagesource.ColdDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

public class Frostbite extends MobEffect {
    public Frostbite() {
        super("frostbite", EffectType.HARMFUL, 203* 231* 241);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 ==0;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.attackEntityFrom(new ColdDamage().setDamageBypassesArmor().setMagicDamage(),(1F + (amplifier)));
    }
}

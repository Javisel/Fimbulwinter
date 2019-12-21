package com.teamcitrus.fimbulwinter.common.objects.effect;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectType;
import net.minecraft.world.World;

import java.awt.*;

public class PhoenixFire extends MobEffect {

    public PhoenixFire() {
        super("phoenix_flame", EffectType.BENEFICIAL, Color.ORANGE.getRGB());
        addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635",  2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributesModifier(SharedMonsterAttributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2, AttributeModifier.Operation.MULTIPLY_TOTAL);

    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 5 == 0;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {

        if (!entityLivingBaseIn.getEntityWorld().isRemote) {
            World world = entityLivingBaseIn.world;

            if (!world.isRemote && world.isAirBlock(entityLivingBaseIn.getPosition())) {

                world.setBlockState(entityLivingBaseIn.getPosition(), Blocks.FIRE.getDefaultState());


            }

        }
    }


}

package com.teamcitrus.fimbulwinter.common.objects.items;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.property.Properties;

public class Frostbow extends FimbulwinterBow {

    private static final String FREEZE = "FREEZE";

    public Frostbow() {
        super("frostbow",new Properties().maxDamage(1255).rarity(Rarity.UNCOMMON));
    }


    @Override
    public void onArrowHitEntity(AbstractArrowEntity arrow, Entity entity) {

        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS,60,3));

        }
    }
}

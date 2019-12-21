package com.teamcitrus.fimbulwinter.common.objects.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

import javax.annotation.Nullable;

public class EntityFireDamage extends EntityDamageSource {
    public EntityFireDamage(@Nullable Entity damageSourceEntityIn) {
        super("entity_fire_damage", damageSourceEntityIn);
        this.setFireDamage();

    }
}

package com.teamcitrus.fimbulwinter.common.objects.damagesource;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

import javax.annotation.Nullable;

public class EntityColdDamage extends EntityDamageSource implements IColdDamage {


    public EntityColdDamage(@Nullable Entity damageSourceEntityIn) {
        super("cold", damageSourceEntityIn);
    }
}

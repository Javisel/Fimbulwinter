package com.teamcitrus.fimbulwinter.common.objects.entities;

import com.teamcitrus.fimbulwinter.common.registration.ItemRegistration;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public  class FrozenSkeleton extends StrayEntity implements IFrostMob {


    public FrozenSkeleton(EntityType<? extends StrayEntity> p_i50194_1_, World p_i50194_2_) {
        super(p_i50194_1_, p_i50194_2_);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        super.setEquipmentBasedOnDifficulty(difficulty);
        //this.setHeldItem(Hand.MAIN_HAND,new ItemStack(ItemRegistration.FROST_BOW,1));
    }
}

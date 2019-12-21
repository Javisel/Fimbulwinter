package com.teamcitrus.fimbulwinter.common.objects.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FWWolf extends WolfEntity {


    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(WolfEntity.class, DataSerializers.VARINT);



    public FWWolf(EntityType<? extends WolfEntity> p_i50240_1_, World p_i50240_2_) {
        super(p_i50240_1_, p_i50240_2_);
    }
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TYPE, MathHelper.nextInt(rand,0,1));
    }

    public int getTextureType() {


        return  this.dataManager.get(TYPE);
    }




}

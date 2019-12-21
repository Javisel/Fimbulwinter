package com.teamcitrus.fimbulwinter.common.objects.entities;

import com.teamcitrus.fimbulwinter.common.objects.damagesource.ColdDamage;
import com.teamcitrus.fimbulwinter.common.objects.damagesource.EntityColdDamage;
import com.teamcitrus.fimbulwinter.common.registration.BlockRegistration;
import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FrozenSpider extends SpiderEntity  implements IFrostMob{
    public static DataParameter<Float> getFROSTCOOLDOWN() {
        return FROSTCOOLDOWN;
    }

    private static final DataParameter<Float> FROSTCOOLDOWN = EntityDataManager.createKey(SpiderEntity.class, DataSerializers.FLOAT);

    public static float getRechargeTime() {
        return rechargeTime;
    }

    private static final float rechargeTime = 300;

    public float getChargePercentage() {

        return  this.dataManager.get(FROSTCOOLDOWN)/rechargeTime;
    }

    public FrozenSpider(World worldIn) {
        super((EntityType<? extends SpiderEntity>) EntityRegistration.FROZEN_SPIDER,worldIn);
    }



    public FrozenSpider(EntityType<Entity> entityEntityType, World world) {
        super((EntityType<? extends SpiderEntity>) EntityRegistration.FROZEN_SPIDER, world);
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.dataManager.set(FROSTCOOLDOWN,rechargeTime);
   /*
    if (worldIn.getRandom().nextInt(100) == 0) {
        SkeletonEntity skeletonentity = EntityType.SKELETON.create(this.world);
        skeletonentity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
        skeletonentity.onInitialSpawn(worldIn, difficultyIn, reason, (ILivingEntityData)null, (CompoundNBT)null);
        worldIn.addEntity(skeletonentity);
        skeletonentity.startRiding(this);
    }
*/
        if (spawnDataIn == null) {
            spawnDataIn = new SpiderEntity.GroupData();
            if (worldIn.getDifficulty() == Difficulty.HARD && worldIn.getRandom().nextFloat() < 0.1F * difficultyIn.getClampedAdditionalDifficulty()) {
                ((SpiderEntity.GroupData) spawnDataIn).setRandomEffect(worldIn.getRandom());
            }
        }

        if (spawnDataIn instanceof SpiderEntity.GroupData) {
            Effect effect = ((SpiderEntity.GroupData) spawnDataIn).effect;
            if (effect != null) {
                this.addPotionEffect(new EffectInstance(effect, Integer.MAX_VALUE));
            }
        }

        return spawnDataIn;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.dataManager.get(FROSTCOOLDOWN) != rechargeTime) {
            this.dataManager.set(FROSTCOOLDOWN, this.dataManager.get(FROSTCOOLDOWN) + 1);

        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if (source.isFireDamage()) {
            amount *= 1.25;
        } else if (source instanceof ColdDamage) {
            amount /= 4;
        }

        return super.attackEntityFrom(source, amount);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(FROSTCOOLDOWN, 0F);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {

        if         ( super.attackEntityAsMob(entityIn)) {


            if (!entityIn.getEntityWorld().isRemote && this.getDataManager().get(FROSTCOOLDOWN) == rechargeTime) {
                entityIn.attackEntityFrom(new EntityColdDamage(this), 2);

                if (entityIn.getEntityWorld().isAirBlock(entityIn.getPosition())) {
                    entityIn.getEntityWorld().setBlockState(entityIn.getPosition(), BlockRegistration.FROZEN_COBWEB.getDefaultState());
                    world.playSound(null, entityIn.getPosition(), BlockRegistration.FROZEN_COBWEB.getSoundType(BlockRegistration.FROZEN_COBWEB.getDefaultState()).getPlaceSound(), SoundCategory.HOSTILE, (BlockRegistration.FROZEN_COBWEB.getDefaultState().getSoundType().getVolume() + 1.0F) / 2.0F, BlockRegistration.FROZEN_COBWEB.getDefaultState().getSoundType().getPitch() * 0.8F);


                } else if (entityIn.getEntityWorld().isAirBlock(entityIn.getPosition().up())) {
                    entityIn.getEntityWorld().setBlockState(entityIn.getPosition().up(), BlockRegistration.FROZEN_COBWEB.getDefaultState());
                    world.playSound(null, entityIn.getPosition().up(), BlockRegistration.FROZEN_COBWEB.getSoundType(BlockRegistration.FROZEN_COBWEB.getDefaultState()).getPlaceSound(), SoundCategory.HOSTILE, (BlockRegistration.FROZEN_COBWEB.getDefaultState().getSoundType().getVolume() + 1.0F) / 2.0F, BlockRegistration.FROZEN_COBWEB.getDefaultState().getSoundType().getPitch() * 0.8F);


                }
                this.getDataManager().set(FROSTCOOLDOWN, 0F);

            }

            return true;
        }
        this.getDataManager().set(FROSTCOOLDOWN, 0F);

return false;
    }
    public void setMotionMultiplier(BlockState p_213295_1_, Vec3d p_213295_2_) {
        if (p_213295_1_.getBlock() != BlockRegistration.FROZEN_COBWEB) {
            super.setMotionMultiplier(p_213295_1_, p_213295_2_);
        }

    }

    @Override
    public void livingTick() {
        super.livingTick();


    }
}

package com.teamcitrus.fimbulwinter.common.objects.entities;

import com.teamcitrus.fimbulwinter.common.objects.damagesource.IColdDamage;
import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FrozenZombie extends MonsterEntity implements IFrostMob{

    protected static final IAttribute FROSTCHANCE = (new RangedAttribute((IAttribute)null, "frostzombie.spawnReinforcements", 1D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");

    public FrozenZombie(World worldIn) {
        super((EntityType<? extends MonsterEntity>) EntityRegistration.FROZEN_ZOMBIE,worldIn);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return super.getLootTable();
    }

    public FrozenZombie(EntityType<Entity> entityEntityType, World world) {
        super((EntityType<? extends MonsterEntity>) EntityRegistration.FROZEN_ZOMBIE, world);
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.applyEntityAI();

    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }
    protected void applyEntityAI() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));

    }

    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23F);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttributes().registerAttribute(FROSTCHANCE).setBaseValue(1);
    }
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);
        if (flag) {

            if (entityIn instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entityIn;

                livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 1));

            }

        }

        return flag;
    }

    public boolean zombieReinforcements(LivingEntity livingentity) {

        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int k = MathHelper.floor(this.posZ);


        FrozenZombie zombieentity = new FrozenZombie(world);
     if (  MathHelper.nextInt(rand,0,100) <= this.getAttribute(FROSTCHANCE).getValue() * 100) {

         for (int l = 0; l < 50; ++l) {
             int i1 = i + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
             int j1 = j + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
             int k1 = k + MathHelper.nextInt(this.rand, 7, 40) * MathHelper.nextInt(this.rand, -1, 1);
             BlockPos blockpos = new BlockPos(i1, j1 - 1, k1);
             if (this.world.getBlockState(blockpos).func_215682_a(this.world, blockpos, zombieentity) && this.world.getLight(new BlockPos(i1, j1, k1)) < 10) {
                 zombieentity.setPosition(i1, j1, k1);
                 if (!this.world.isPlayerWithin(i1, j1, k1, 7.0D) && this.world.checkNoEntityCollision(zombieentity) && this.world.areCollisionShapesEmpty(zombieentity) && !this.world.containsAnyLiquid(zombieentity.getBoundingBox())) {
                     this.world.addEntity(zombieentity);
                     if (livingentity != null)
                         zombieentity.setAttackTarget(livingentity);
                     zombieentity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(zombieentity)), SpawnReason.MOB_SUMMONED, null, null);
                     this.getAttribute(FROSTCHANCE).applyModifier(new AttributeModifier("called zombie",-0.5F, AttributeModifier.Operation.ADDITION));

                     zombieentity.getAttribute(FROSTCHANCE).setBaseValue(this.getAttribute(FROSTCHANCE).getBaseValue()/2);
                     return true;
                 }
             }

         }

     }

        return false;

    }


    @Override
    public void baseTick() {
        super.baseTick();
        if (!world.isRemote) {
            FrostWalkerEnchantment.freezeNearby(this, world, this.getPosition(), 1);

        }
    }



    public boolean attackEntityFrom(DamageSource source, float amount) {


        if (!world.isRemote) {
            if (source instanceof IColdDamage) {
                amount = 0;
            } else if (source.isFireDamage()) {
                amount *= 1.25;
            }


            if (source.getTrueSource() instanceof LivingEntity) {

                if (zombieReinforcements((LivingEntity) source.getTrueSource())) {


                }
            }


        }

        return  super.attackEntityFrom(source,amount);
    }


}

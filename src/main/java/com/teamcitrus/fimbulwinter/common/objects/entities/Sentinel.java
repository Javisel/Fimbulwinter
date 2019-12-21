package com.teamcitrus.fimbulwinter.common.objects.entities;

import com.teamcitrus.fimbulwinter.common.registration.BlockRegistration;
import com.teamcitrus.fimbulwinter.common.registration.EffectRegistration;
import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class Sentinel extends MonsterEntity implements IFrostMob {
    private static final DataParameter<Integer> CHARGEUP = EntityDataManager.createKey(Sentinel.class, DataSerializers.VARINT);
    //0 - Dormant
    //1 - Charging
    //2 - Living
    //3 Rage
    private static final DataParameter<Integer> STATE = EntityDataManager.createKey(Sentinel.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> ASTAGE = EntityDataManager.createKey(Sentinel.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SPINNING = EntityDataManager.createKey(Sentinel.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SPINTICK = EntityDataManager.createKey(Sentinel.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TOTALSPINS = EntityDataManager.createKey(Sentinel.class, DataSerializers.VARINT);

    // 0 - Melee Attacking
    // 1 - Spin-Windup
    //2 - Spin
    // 3 - Spin Cooldown
    // 4 - Spin
    //Counter for "Praise" - Sentinel lifting it's arms
    private static final DataParameter<Integer> PRAISE = EntityDataManager.createKey(Sentinel.class, DataSerializers.VARINT);
    //Spin
    private static int maxSpin = 10;
    private static float rangedDamageReduction = 0.60F;
    //Praise Attack
    private static int praiseLimit = 35;
    private static int praiseRange = 20;
    private final ServerBossInfo bossInfo = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
    private List<BlockPos> subjectareas = new ArrayList<>();
    //0 - Private Data
    private int attackTimer = 0;
    private int attacks = 0;
    private double hurlheight = 0.6;
    private int spinHitThreshold = 10;
    private float spinDamage = 4F;
    private int spinRange = 3;
    private int timeSinceAttack = 0;
    private int attacklessLimit = 100;

    public Sentinel(World worldIn) {
        super((EntityType<? extends MonsterEntity>) EntityRegistration.SENTINEL, worldIn);
        this.experienceValue = 50;
        this.setHealth(1);
        this.dataManager.set(STATE, 1);
        this.dataManager.set(ASTAGE, 0);
        this.dataManager.set(PRAISE, 0);
        this.dataManager.set(TOTALSPINS, 0);
        this.dataManager.set(SPINTICK, 0);

        this.canBreatheUnderwater();
        this.getNavigator().setCanSwim(true);
        this.getNavigator().getNodeProcessor().setCanSwim(true);
        this.stepHeight = 5;
    }

    public Sentinel(EntityType<Entity> entityEntityType, World world) {
        super((EntityType<? extends MonsterEntity>) EntityRegistration.SENTINEL, world);
        this.experienceValue = 50;
        this.setHealth(1);
        this.dataManager.set(STATE, 1);
        this.dataManager.set(ASTAGE, 0);
        this.dataManager.set(PRAISE, 0);
        this.dataManager.set(TOTALSPINS, 0);
        this.dataManager.set(SPINTICK, 0);

        this.getNavigator().getNodeProcessor().setCanSwim(true);
        this.stepHeight = 5;

        this.getNavigator().setCanSwim(true);
    }

    @Override
    protected float getWaterSlowDown() {
        return 1;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public void heal(float healAmount) {


        super.heal(healAmount);
    }

    public int getCHARGEUP() {
        return dataManager.get(CHARGEUP);
    }

    public int getSTATE() {
        return dataManager.get(STATE);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.experienceValue = 50;
        this.setHealth(1);
        this.dataManager.set(STATE, 1);
        this.dataManager.set(ASTAGE, 0);
        this.dataManager.set(PRAISE, 0);
        this.dataManager.set(TOTALSPINS, 0);
        this.dataManager.set(SPINTICK, 0);

        this.getNavigator().setCanSwim(true);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }


    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("CHARGEUP", this.dataManager.get(CHARGEUP));
        compound.putInt("STATE", this.dataManager.get(STATE));
        compound.putInt("ASTAGE", this.dataManager.get(ASTAGE));
        compound.putBoolean("SPINNING", this.dataManager.get(SPINNING));
        compound.putInt("PRAISE", this.dataManager.get(PRAISE));
        compound.putInt("SPINTICK", this.dataManager.get(SPINTICK));
        compound.putInt("SPINS", this.dataManager.get(TOTALSPINS));


        super.writeAdditional(compound);

    }

    @Override
    protected void checkDespawn() {
        this.idleTime = 0;
    }

    public void readAdditional(CompoundNBT compound) {
        this.dataManager.set(CHARGEUP, compound.getInt("CHARGEUP"));
        this.dataManager.set(STATE, compound.getInt("STATE"));
        this.dataManager.set(ASTAGE, compound.getInt("ASTAGE"));

        this.dataManager.set(SPINNING, compound.getBoolean("SPINNING"));
        this.dataManager.set(PRAISE, compound.getInt("PRAISE"));
        this.dataManager.set(SPINTICK, this.dataManager.get(SPINTICK));

        super.readAdditional(compound);

    }

    public boolean setRage() {

        if (this.getHealth() < this.getMaxHealth() / 4 && this.dataManager.get(STATE) != 3) {


            bossInfo.setColor(BossInfo.Color.RED);
            this.dataManager.set(STATE, 3);
            this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).applyModifier(new AttributeModifier("RAGE", 2, AttributeModifier.Operation.ADDITION));
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier("RAGE", 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
            hurlheight += 0.2;
            spinDamage += 2;


            return true;
        }
        return false;


    }


    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(1, new SentinelAttack(this, 0.5, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));

        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 25, 1.0F));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));

        this.goalSelector.addGoal(0, new DoNothingGoal());

    }


    @Override
    protected void registerAttributes() {
        super.registerAttributes();


        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8);
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_KNOCKBACK).setBaseValue(1D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getAttribute(SWIM_SPEED).setBaseValue(1);
    }


    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CHARGEUP, 0);
        this.dataManager.register(STATE, 0);
        this.dataManager.register(ASTAGE, 0);
        this.dataManager.register(SPINNING, false);
        this.dataManager.register(SPINTICK, 0);
        this.dataManager.register(TOTALSPINS, 0);

        this.dataManager.register(PRAISE, 0);

    }

    public boolean isPotionApplicable(EffectInstance potioneffectIn) {
        return potioneffectIn.getPotion().isBeneficial();
    }

    public boolean isSpinning() {


        return this.dataManager.get(SPINNING);
    }

    public Integer getBattleStage() {


        return this.dataManager.get(ASTAGE);
    }


    @Override
    public CreatureAttribute getCreatureAttribute() {

        return CreatureAttribute.UNDEFINED;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return super.getSwimSound();
    }

    @Override
    protected SoundEvent getSplashSound() {
        return super.getSplashSound();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return super.getHurtSound(damageSourceIn);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if (this.dataManager.get(CHARGEUP) != 10 || source.getTrueSource() == this || source == DamageSource.FALL || source == DamageSource.FALLING_BLOCK || source == DamageSource.IN_WALL || (this.getDataManager().get(ASTAGE) == 1 && source.isProjectile())) {
            return false;
        }
        if (source.isProjectile()) {
            amount -= amount * rangedDamageReduction;
            if (amount <= 0) return false;
        }
        if (source.isFireDamage()) {
            amount *= 1.25;
        }


        if (super.attackEntityFrom(source, amount)) {

            setRage();

            return true;
        }

        return false;
    }

    @Override
    public boolean isNonBoss() {
        return false;
    }

    protected void updateAITasks() {
        if (this.dataManager.get(STATE) != 0) {
            if (this.dataManager.get(CHARGEUP) < 10) {


                if (this.ticksExisted % 20 == 0) {
                    this.heal(this.getMaxHealth() / 10);
                    this.dataManager.set(CHARGEUP, this.dataManager.get(CHARGEUP) + 1);
                    int j1 = this.dataManager.get(CHARGEUP);
                    if (j1 == 10) {
                        this.dataManager.set(STATE, 2);
                        Explosion explosion = new Explosion(world, this, posX, posY, posZ, 10, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
                        explosion.doExplosionA();

                        explosion.doExplosionB(true);

                        this.setNoGravity(true);
                        Iterator it = explosion.getAffectedBlockPositions().iterator();

                        while (it.hasNext()) {

                            BlockPos pos = (BlockPos) it.next();
                            if (pos.getY() == this.posY - 1) {
                                world.setBlockState(pos.down(), rand.nextInt(100) >= 25 ? Blocks.SNOW_BLOCK.getDefaultState() : Blocks.ICE.getDefaultState());
                            }


                        }
                        this.setNoGravity(false);

                        this.world.playBroadcastSound(1023, new BlockPos(this), 0);


                    }
                }

            } else {
                super.updateAITasks();


            }
        }
    }

    protected boolean canBeRidden(Entity entityIn) {
        return false;
    }

    public void addTrackingPlayer(ServerPlayerEntity player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    public void removeTrackingPlayer(ServerPlayerEntity player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    public void progressStages() {


        if (this.getDataManager().get(ASTAGE) >= 2) {
            subjectareas.clear();

            this.getDataManager().set(ASTAGE, 0);
        } else {
            if (this.getAttackTarget() == null) {
                this.getDataManager().set(ASTAGE, 0);

            } else {
                this.getDataManager().set(ASTAGE, this.getDataManager().get(ASTAGE) + 1);
            }
        }
        if (this.getDataManager().get(ASTAGE) == 0) {


        } else if (this.getDataManager().get(ASTAGE) == 1) {
            attacks = 0;
            timeSinceAttack = 0;
            this.addPotionEffect(new EffectInstance(Effects.SPEED, maxSpin * 10, 3, false, false));
            this.dataManager.set(SPINNING, true);
            this.world.setEntityState(this, (byte) 4);

        } else if (this.getDataManager().get(ASTAGE) == 2) {
            this.dataManager.set(SPINNING, false);


        }


    }


    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
        return this.attackTimer;
    }


    @Override
    public boolean attackEntityAsMob(Entity entityIn) {


        if (this.dataManager.get(ASTAGE) != 0) {
            return false;
        }
        this.attackTimer = 10;
        this.world.setEntityState(this, (byte) 4);

        boolean hashit = super.attackEntityAsMob(entityIn);

        if (hashit) {

            this.world.setEntityState(this, (byte) 4);

            entityIn.setMotion(entityIn.getMotion().add(0.0D, hurlheight, 0.0D));


            if (entityIn instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entityIn;

                livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 4));
                if (getSTATE() == 3) {
                    livingEntity.addPotionEffect(new EffectInstance(EffectRegistration.VULNERABILITY, 60, 0));
                }
            }
        }
        attacks++;
        timeSinceAttack = 0;
        if (attacks == 3) {
            progressStages();
        }
        return hashit;
    }


    public int getSpin() {

        return dataManager.get(SPINTICK);
    }


    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            if (dataManager.get(ASTAGE) == 0) {
                attackTimer = 10;
            } else if (dataManager.get(ASTAGE) == 1) {

            }
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        } else if (id == 8) {
            this.playSound(SoundEvents.ENTITY_IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleStatusUpdate(id);
        }

    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (attackTimer != 0) {
            attackTimer--;
        }
    }

    public float getPercentSpin() {


        return 6.28318530718F * (((float) getSpin() / spinHitThreshold));

    }

    public float getPercentPraise() {


        return -2.0F * (((float) dataManager.get(PRAISE) / praiseLimit));
    }


    @Override
    public void baseTick() {

        super.baseTick();

        if (!world.isRemote) {
            if (world.getDifficulty() == Difficulty.PEACEFUL) {
                this.remove();
            }
            FrostWalkerEnchantment.freezeNearby(this, world, this.getPosition(), 8);
            updateBossBar();


        }
        if (dataManager.get(CHARGEUP) != 10) {
            return;
        }

        if (dataManager.get(ASTAGE) == 0) {
            strikeAttack();

        } else if (dataManager.get(ASTAGE) == 1) {
            spinAttack();
        } else if (dataManager.get(ASTAGE) == 2) {

            conjureAttack();


        }


    }


    private void strikeAttack() {

        if (!world.isRemote) {
            timeSinceAttack++;
            if (timeSinceAttack % attacklessLimit == 0) {
                progressStages();
            }
            if (timeSinceAttack > 600) {
                setRage();
                timeSinceAttack = 0;
            }

        }

    }

    private void spinAttack() {

        if (!world.isRemote) {
            dataManager.set(SPINTICK, dataManager.get(SPINTICK) + 1);
            if (dataManager.get(SPINTICK) >= spinHitThreshold) {
                dataManager.set(SPINTICK, 0);
                dataManager.set(TOTALSPINS, dataManager.get(TOTALSPINS) + 1);
                List<Entity> entities = world.getEntitiesInAABBexcluding(this, new AxisAlignedBB(this.getPosition().add(spinRange * -1, spinRange * -1, spinRange * -1), this.getPosition().add(spinRange, spinRange, spinRange)), EntityPredicates.CAN_AI_TARGET);


                for (Entity entity : entities) {

                    entity.hurtResistantTime = 0;
                    if (entity instanceof LivingEntity) {

                        entity.attackEntityFrom(DamageSource.causeMobDamage(this), spinDamage);

                        ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 4));

                        if (entity instanceof PlayerEntity) {

                            PlayerEntity player = (PlayerEntity) entity;

                            if (player.getActiveItemStack().getItem() == Items.SHIELD) {

                                player.getActiveItemStack().getItem().damageItem(activeItemStack, 35, null, null);

                            }

                        }
                    }
                }

                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                    for (int x = spinRange * -1; x < spinRange; x++) {

                        for (int y = 0; y < spinRange; y++) {


                            for (int z = spinRange * -1; z < spinRange; z++) {

                                if (!world.isAirBlock(this.getPosition().add(x, y, z))) {

                                    if (!BlockTags.WITHER_IMMUNE.contains(world.getBlockState(this.getPosition().add(x, y, z)).getBlock())) {
                                        world.destroyBlock(this.getPosition().add(x, y, z), false);

                                    }

                                }


                            }
                        }

                    }
                }


            }


            if (dataManager.get(TOTALSPINS) >= maxSpin) {
                dataManager.set(TOTALSPINS, 0);
                progressStages();
            }
        } else {

        }
    }


    private void conjureAttack() {

        if (!world.isRemote) {
            if (dataManager.get(PRAISE) == 0) {

                List<Entity> entities = world.getEntitiesInAABBexcluding(this, new AxisAlignedBB(this.getPosition().add(praiseRange * -1, praiseRange * -1, praiseRange * -1), this.getPosition().add(praiseRange, praiseRange, praiseRange)), EntityPredicates.CAN_AI_TARGET);


                for (Entity entity : entities) {

                    if (entity instanceof LivingEntity && entity.isAlive() && this.canAttack(entity.getType())) {

                        subjectareas.add(entity.getPosition().add(0, entity.getHeight(), 0));
                        ((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 7));


                    }


                }

                if (subjectareas.isEmpty()) {
                    progressStages();
                } else {
                    dataManager.set(PRAISE, 1);

                }
            } else {
                dataManager.set(PRAISE, dataManager.get(PRAISE) + 1);

            }


            if (dataManager.get(PRAISE) >= praiseLimit) {
                for (BlockPos pos : subjectareas) {


                    for (int x = -2; x < 2; x++) {

                        for (int y = 3; y < 11; y++) {

                            for (int z = -2; z < 2; z++) {


                                if (!world.isAirBlock(pos.add(x, y, z)) || y == 10) {


                                    world.setBlockState(pos.add(x, y, z), getSTATE() == 3 ? BlockRegistration.FREEZING_SNOW.getDefaultState() : BlockRegistration.SOFT_SNOW.getDefaultState());

                                }


                            }

                        }


                    }

                }
                dataManager.set(PRAISE, 0);
                subjectareas.clear();
                progressStages();

            }
        } else {
            if (dataManager.get(PRAISE) == 0) {

                List<Entity> entities = world.getEntitiesInAABBexcluding(this, new AxisAlignedBB(this.getPosition().add(praiseRange * -1, praiseRange * -1, praiseRange * -1), this.getPosition().add(praiseRange, praiseRange, praiseRange)), EntityPredicates.CAN_AI_TARGET);

                List<LivingEntity> livingEntities = new ArrayList<>();


                if (!entities.isEmpty()) {
                    for (Entity entity : entities) {

                        if (entity instanceof LivingEntity) {
                            livingEntities.add((LivingEntity) entity);
                        }

                    }

                    if (!livingEntities.isEmpty()) {
                        for (LivingEntity entity : livingEntities) {
                            if (entity.isAlive()) {

                                subjectareas.add(entity.getPosition().add(0, entity.getHeight(), 0));
                                entity.world.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, SoundCategory.HOSTILE, 1, 1, true);
                                for (int x = -2; x < 2; x++) {

                                    for (int y = 0; y < 10; y++) {
                                        for (int z = -2; z < 2; z++) {

                                            for (int i = 0; i < 10; i++) {


                                                world.addParticle(MathHelper.nextInt(rand, 0, 100) < 50 ? new BlockParticleData(ParticleTypes.BLOCK, this.getSTATE() == 3 ? BlockRegistration.FREEZING_SNOW.getDefaultState() : BlockRegistration.SOFT_SNOW.getDefaultState()) : ParticleTypes.MYCELIUM, (float) entity.posX + x, (float) entity.posY + y, (float) entity.posZ + z, 0.0D, 0.0D, 0.0D);


                                            }

                                        }
                                    }

                                }


                            }


                        }
                    }
                }


            }


        }


    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return super.canAttack(target) && !(target instanceof IFrostMob);
    }

    public void updateBossBar() {


        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        if (this.getSTATE() == 1) {

            bossInfo.setColor(BossInfo.Color.WHITE);
        } else if (this.getSTATE() == 2) {
            bossInfo.setColor(BossInfo.Color.BLUE);


        } else if (this.getSTATE() == 3) {
            bossInfo.setColor(BossInfo.Color.RED);

            this.bossInfo.setName(this.getDisplayName().applyTextStyle(TextFormatting.DARK_RED).applyTextStyle(TextFormatting.BOLD));

        } else {
            bossInfo.setColor(BossInfo.Color.PURPLE);
        }


    }


    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        ItemEntity itementity = this.entityDropItem(Item.getItemFromBlock(BlockRegistration.WINTERFALL_PORTAL));
        if (itementity != null) {
            itementity.setNoDespawn();
            itementity.setInvulnerable(true);
        }
    }

    class DoNothingGoal extends Goal {
        public DoNothingGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            return Sentinel.this.dataManager.get(CHARGEUP) < 10;
        }
    }

    class SentinelAttack extends MeleeAttackGoal {


        public SentinelAttack(Sentinel sentinel, double speedIn, boolean useLongMemory) {
            super(sentinel, speedIn, useLongMemory);

        }

        @Override
        public boolean shouldExecute() {
            return attacker.getDataManager().get(ASTAGE) <= 1 && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return attacker.getDataManager().get(ASTAGE) <= 1 && super.shouldContinueExecuting();
        }
    }
}

package com.teamcitrus.fimbulwinter.common.objects.entities;

import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.HandSide;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class IceCrystal extends MobEntity implements IFrostMob{
    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(IceCrystal.class, DataSerializers.VARINT);
    @Nullable
    private LivingEntity tntPlacedBy;
    private int fuse = 100;
    private float explosionSize = 4.0F;


    public IceCrystal(World world, double x,double y, double z, int fuse, float explosionSize, LivingEntity placer){
        super((EntityType<? extends MobEntity>) EntityRegistration.ICE_CRYSTAL,world);
       this.setPosition(x,y,z);
        this.fuse=fuse;
        this.explosionSize=explosionSize;
        tntPlacedBy=placer;
    }


    public IceCrystal(EntityType<Entity> entityEntityType, World world) {
        super( (EntityType<? extends MobEntity>) EntityRegistration.ICE_CRYSTAL,world);

    }





    @Override
    protected void registerAttributes(){
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15);
        this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2);
    }


    @Override
    protected void playHurtSound(DamageSource source) {
        super.playHurtSound(source);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_GLASS_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_GLASS_HIT;
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(FUSE, fuse);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return !this.dead;
    }

    @Override
    public HandSide getPrimaryHand() {
        return HandSide.LEFT;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();


        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            if (!this.world.isRemote) {
              //  this.explode();
            }
        } else {
            this.world.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.ICE.getDefaultState()), this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);

        }

    }

    protected void explode() {

        if (!world.isRemote) {
            Explosion explosion = new Explosion(world, tntPlacedBy, posX, posY, posZ, explosionSize, false, net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            explosion.doExplosionA();
            explosion.doExplosionB(true);
            List<BlockPos> blocks = explosion.getAffectedBlockPositions();

            for(BlockPos pos : blocks) {

                if (world.isAirBlock(pos) && MathHelper.nextInt(rand,0,100)<=5) {

                    world.setBlockState(pos,Blocks.ICE.getDefaultState());



                }


            }



        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {


        return false;
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, cause)) return;
        if (!this.dead) {
            Entity entity = cause.getTrueSource();
            LivingEntity livingentity = this.getAttackingEntity();
            if (this.scoreValue >= 0 && livingentity != null) {
                livingentity.awardKillScore(this, this.scoreValue, cause);
            }

            if (entity != null) {
                entity.onKillEntity(this);
            }

            if (this.isSleeping()) {
                this.wakeUp();
            }

            this.dead = true;
            this.getCombatTracker().reset();
            if (!this.world.isRemote) {
                this.spawnDrops(cause);
                boolean flag = false;
                if (livingentity instanceof WitherEntity) {
                    if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
                        BlockState blockstate = Blocks.WITHER_ROSE.getDefaultState();
                        if (this.world.getBlockState(blockpos).isAir() && blockstate.isValidPosition(this.world, blockpos)) {
                            this.world.setBlockState(blockpos, blockstate, 3);
                            flag = true;
                        }
                    }

                    if (!flag) {
                        ItemEntity itementity = new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(Items.WITHER_ROSE));
                        this.world.addEntity(itementity);
                    }
                }




            } else {
                    for(int i =0; i <10; i ++) {

                        world.addParticle(new BlockParticleData(ParticleTypes.BLOCK,Blocks.ICE.getDefaultState()),posX,posY,posZ,0,0,0);

                    }
            }

            this.world.setEntityState(this, (byte)3);
           // this.setPose(Pose.DYING);
        }

    }

    public void writeAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
    }



    /**
     * returns null or the entityliving it was placed or ignited by
     */
    @Nullable
    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }


    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }

    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }

    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}
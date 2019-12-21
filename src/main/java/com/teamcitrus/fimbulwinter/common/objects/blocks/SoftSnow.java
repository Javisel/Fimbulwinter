package com.teamcitrus.fimbulwinter.common.objects.blocks;

import com.teamcitrus.fimbulwinter.common.objects.damagesource.ColdDamage;
import com.teamcitrus.fimbulwinter.common.objects.entities.IFrostMob;
import com.teamcitrus.fimbulwinter.common.objects.entities.Sentinel;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class SoftSnow extends FallingBlock {
    public SoftSnow() {
        super( Properties.create(Material.SNOW,MaterialColor.WHITE_TERRACOTTA).doesNotBlockMovement().sound(SoundType.SNOW).hardnessAndResistance(0.5F,0.5F));
        setRegistryName(Fimbulwinter.MODID,"soft_snow");
    }
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.isBurning()) {
            worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
        } else {
            if (!(entityIn instanceof IFrostMob) && !(entityIn instanceof Sentinel) && entityIn instanceof LivingEntity)  {


                    entityIn.setMotion(entityIn.getMotion().mul(0.2D, 0.2D, 0.2D));



            }
        }
    }


    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            this.checkFallable(worldIn, pos);
        }

    }
    private void checkFallable(World worldIn, BlockPos pos) {
        if (worldIn.isAirBlock(pos.down())&& pos.getY() >= 0) {
            if (!worldIn.isRemote) {
                FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
                this.onStartFalling(fallingblockentity);
                worldIn.addEntity(fallingblockentity);
            }

        }
    }

            @Override
    protected void onStartFalling(FallingBlockEntity fallingEntity) {
        fallingEntity.setHurtEntities(true);
        super.onStartFalling(fallingEntity);
    }

    @Override
    public void onEndFalling(World worldIn, BlockPos pos, BlockState fallingState, BlockState hitState) {
        super.onEndFalling(worldIn, pos, fallingState, hitState);
    }
}

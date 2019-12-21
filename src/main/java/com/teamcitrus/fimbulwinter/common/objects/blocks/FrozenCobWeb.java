package com.teamcitrus.fimbulwinter.common.objects.blocks;

import com.teamcitrus.fimbulwinter.common.objects.damagesource.ColdDamage;
import com.teamcitrus.fimbulwinter.common.objects.entities.IFrostMob;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class FrozenCobWeb extends WebBlock {
    public FrozenCobWeb() {
        super(Block.Properties.create(Material.WEB).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.SNOW));
        setRegistryName(Fimbulwinter.MODID, "frozen_cobweb");

    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn.isBurning()) {
            worldIn.setBlockState(pos, Blocks.FIRE.getDefaultState());
        } else {
          if (!(entityIn instanceof IFrostMob))  {
              entityIn.attackEntityFrom(new ColdDamage(), 1F);
              super.onEntityCollision(state, worldIn, pos, entityIn);

          }
        }
    }
}

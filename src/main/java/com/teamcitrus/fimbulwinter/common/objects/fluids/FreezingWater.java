package com.teamcitrus.fimbulwinter.common.objects.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.tags.Tag;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class FreezingWater extends WaterFluid {



    @Override
    public boolean isSource(IFluidState state) {
        return false;
    }

    @Override
    public int getLevel(IFluidState p_207192_1_) {
        return 0;
    }

    @Override
    public Fluid getFluid() {
        return null;
    }

    @Override
    public boolean isEntityInside(IFluidState state, IWorldReader world, BlockPos pos, Entity entity, double yToTest, Tag<Fluid> tag, boolean testingHead) {
        return false;
    }

    @Nullable
    @Override
    public Boolean isAABBInsideMaterial(IFluidState state, IWorldReader world, BlockPos pos, AxisAlignedBB boundingBox, Material materialIn) {
        return null;
    }

    @Nullable
    @Override
    public Boolean isAABBInsideLiquid(IFluidState state, IWorldReader world, BlockPos pos, AxisAlignedBB boundingBox) {
        return null;
    }

    @Override
    public float getExplosionResistance(IFluidState state, IWorldReader world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        return 0;
    }

    @Override
    public boolean canRenderInLayer(IFluidState state, BlockRenderLayer layer) {
        return false;
    }
}

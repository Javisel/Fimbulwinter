package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IHeatItem {


    public double getHeatGen(ItemStack stack);

    public double getHeatCost(ItemStack stack);

    public int getCooldown(ItemStack stack);
    boolean canCast(ItemStack stack, PlayerEntity caster);

}

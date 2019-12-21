package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.resources.IFutureReloadListener;

public class ArrowData implements IArrowData {

    private ItemStack bow;

    @Override
    public ItemStack getBow() {
        return bow;
    }

    @Override
    public void setBow(ItemStack stack) {

        if (!(stack.getItem() instanceof BowItem)) {

            System.err.println("Invalid Item set to Bow!");

        } else {
            bow = stack;
        }
    }

    @Override
    public void loadNBTData(CompoundNBT nbtTag) {
        bow=ItemStack.read(nbtTag.getCompound("BOW"));
    }

    @Override
    public CompoundNBT saveNBTData() {
        CompoundNBT nbt = new CompoundNBT();
        CompoundNBT bowtag = new CompoundNBT();
        bow.write(bowtag);
        nbt.put("BOW",bowtag);
        return  nbt;

    }
}

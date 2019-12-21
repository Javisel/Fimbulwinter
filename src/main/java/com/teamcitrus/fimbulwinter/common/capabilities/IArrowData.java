package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public interface IArrowData {

     ItemStack getBow();
     void setBow(ItemStack stack);

    void loadNBTData(CompoundNBT nbtTag);

    CompoundNBT saveNBTData();

}

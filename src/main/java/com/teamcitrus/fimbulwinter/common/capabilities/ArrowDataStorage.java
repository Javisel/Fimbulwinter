package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class ArrowDataStorage implements Capability.IStorage<IArrowData> {


    @Nullable
    @Override
    public INBT writeNBT(Capability<IArrowData> capability, IArrowData instance, Direction side) {
        return instance.saveNBTData();
    }

    @Override
    public void readNBT(Capability<IArrowData> capability, IArrowData instance, Direction side, INBT nbt) {

        instance.loadNBTData((CompoundNBT) nbt);
    }
}


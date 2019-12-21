package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PlayerDataStorage implements Capability.IStorage<IPlayerData> {


    @Nullable
    @Override
    public INBT writeNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side) {
        return instance.saveNBTData();
    }

    @Override
    public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side, INBT nbt) {

        instance.loadNBTData((CompoundNBT) nbt);
    }
}


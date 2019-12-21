package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.nbt.CompoundNBT;

public interface IPlayerData {

    double getHeat();

    void setHeat(double amount);

    double getMaxHeat();

    void setMaxHeat(double amount);

    void addHeat(double amount);

    void setCurrentEntropy(double amount);

    double getEntropy();

    double getstartEntropyTime();

    void tick();

    void loadNBTData(CompoundNBT nbtTag);

    CompoundNBT saveNBTData();


}

package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.nbt.CompoundNBT;

public class PlayerData implements IPlayerData {

    private double currentHeat = 0;
    private double maxHeat = 100;
    private double currentEntropy = 0;
    private double startEntropyTime = 10;
    private int ticks = 0;

    @Override
    public double getHeat() {
        return currentHeat;
    }

    @Override
    public void setHeat(double amount) {
        currentHeat = amount;
    }

    @Override
    public double getMaxHeat() {
        return maxHeat;
    }

    @Override
    public void setMaxHeat(double amount) {
        maxHeat = amount;
    }

    @Override
    public void addHeat(double amount) {

        if (currentHeat + amount > maxHeat) {
            currentHeat = maxHeat;
        } else if (currentHeat + amount < 0) {
            currentHeat = 0;
        } else {
            currentHeat += amount;
        }
    }

    @Override
    public void setCurrentEntropy(double amount) {
        currentEntropy = amount;
    }


    public void tick() {

        if (ticks == 20) {
            ticks = 0;

            if (currentEntropy == 0) {
                addHeat(maxHeat * -0.01);
            } else {
                currentEntropy--;
            }
        } else {
            ticks++;
        }


    }

    @Override
    public void loadNBTData(CompoundNBT nbtTag) {
        currentHeat = nbtTag.getDouble("currentHeat");
        maxHeat = nbtTag.getDouble("maxHeat");
        currentEntropy = nbtTag.getDouble("heatCountdown");

    }

    @Override
    public CompoundNBT saveNBTData() {

        CompoundNBT nbt = new CompoundNBT();

        nbt.putDouble("currentHeat", currentHeat);
        nbt.putDouble("maxHeat", maxHeat);
        nbt.putDouble("heatCountdown", currentEntropy);

        return nbt;
    }

    @Override
    public double getstartEntropyTime() {
        return startEntropyTime;
    }

    @Override
    public double getEntropy() {
        return currentEntropy;
    }
}

package com.teamcitrus.fimbulwinter.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArrowDataProvider implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(IArrowData.class)
    public static Capability<IArrowData> Arrow_DATA_CAPABILITY = null;

    private LazyOptional<IArrowData> instance = LazyOptional.of(Arrow_DATA_CAPABILITY::getDefaultInstance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == Arrow_DATA_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return (LazyOptional<T>) instance;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) Arrow_DATA_CAPABILITY.getStorage().writeNBT(Arrow_DATA_CAPABILITY, this.instance.orElseThrow(NullPointerException::new), null);

    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        Arrow_DATA_CAPABILITY.getStorage().readNBT(Arrow_DATA_CAPABILITY, this.instance.orElseThrow(NullPointerException::new), null, nbt);

    }
}

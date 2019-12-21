package com.teamcitrus.fimbulwinter.client.particle;

import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;

public class GehenniteKatanExplosionData implements IParticleData {
    @Override
    public ParticleType<?> getType() {
        return ParticleTypes.FLAME;
    }

    @Override
    public void write(PacketBuffer buffer) {

    }

    @Override
    public String getParameters() {
        return null;
    }
}

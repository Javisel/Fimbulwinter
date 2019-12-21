package com.teamcitrus.fimbulwinter.common.world.winterfall.biome;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.world.biome.Biome;

public class BiomeBase extends Biome {
    protected BiomeBase(String name,Builder biomeBuilder) {
        super(biomeBuilder);
        setRegistryName(Fimbulwinter.MODID,name);
    }
}

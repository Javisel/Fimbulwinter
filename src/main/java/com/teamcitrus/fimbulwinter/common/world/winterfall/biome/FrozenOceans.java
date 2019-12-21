package com.teamcitrus.fimbulwinter.common.world.winterfall.biome;

import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class FrozenOceans  extends BiomeBase {

    public FrozenOceans() {
        super("frozen_ocean",(new Biome.Builder()).surfaceBuilder(SurfaceBuilder.FROZEN_OCEAN, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.1F).temperature(0.5F).downfall(0.5F).waterColor(4020182).waterFogColor(329011).parent((String)null));

        this.getSpawns(EntityClassification.MONSTER).clear();;
        this.getSpawns(EntityClassification.WATER_CREATURE).clear();
        this.getSpawns(EntityClassification.MONSTER).add(new SpawnListEntry(EntityRegistration.FROZEN_ZOMBIE,100,4,10));


    }





}

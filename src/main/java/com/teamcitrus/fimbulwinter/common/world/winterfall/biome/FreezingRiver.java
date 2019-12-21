package com.teamcitrus.fimbulwinter.common.world.winterfall.biome;

import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class FreezingRiver extends BiomeBase {
    public FreezingRiver() {
        super("freezing_river", (new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.RIVER).depth(-0.5F).scale(0.0F).temperature(0.5F).downfall(0.5F).waterColor(4159204).waterFogColor(329011).parent((String) null));
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
      //  DefaultBiomeFeatures.addFreezeTopLayer(this);
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistration.FROZEN_ZOMBIE, 100, 4, 12));

    }
}
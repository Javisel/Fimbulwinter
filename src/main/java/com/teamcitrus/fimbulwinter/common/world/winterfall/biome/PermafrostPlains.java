package com.teamcitrus.fimbulwinter.common.world.winterfall.biome;

import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class PermafrostPlains extends BiomeBase {
    public PermafrostPlains() {
        super("permafrost_plains",(new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY).depth(0.125F).scale(0.05F).temperature(0.0F).downfall(0.5F).waterColor(4159204).waterFogColor(329011).parent((String)null));

        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
    DefaultBiomeFeatures.addFreezeTopLayer(this);

        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistration.FROZEN_SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistration.FROZEN_ZOMBIE, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityRegistration.FROZEN_CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.STRAY, 100, 4, 4));

    }


}

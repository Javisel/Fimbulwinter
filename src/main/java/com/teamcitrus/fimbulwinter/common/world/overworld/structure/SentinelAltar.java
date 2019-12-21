package com.teamcitrus.fimbulwinter.common.world.overworld.structure;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IglooPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;
import java.util.function.Function;

public class SentinelAltar extends Structure<NoFeatureConfig> {

    public SentinelAltar(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_) {
        super(p_i51427_1_);
    }

    @Override
    public boolean hasStartAt(ChunkGenerator chunkGenerator, Random random, int i, int i1) {
        return false;
    }

    @Override
    public IStartFactory getStartFactory() {
        return SentinelAltar.Start::new;
    }

    @Override
    public String getStructureName() {
        return "Sentinel Altar";
    }

    @Override
    public int getSize() {
        return 10;
    }
    public static class Start extends StructureStart {
        public Start(Structure<?> p_i50678_1_, int p_i50678_2_, int p_i50678_3_, Biome p_i50678_4_, MutableBoundingBox p_i50678_5_, int p_i50678_6_, long p_i50678_7_) {
            super(p_i50678_1_, p_i50678_2_, p_i50678_3_, p_i50678_4_, p_i50678_5_, p_i50678_6_, p_i50678_7_);
        }

        public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
            NoFeatureConfig nofeatureconfig = (NoFeatureConfig)generator.getStructureConfig(biomeIn, Feature.IGLOO);
            int i = chunkX * 16;
            int j = chunkZ * 16;
            BlockPos blockpos = new BlockPos(i, 90, j);
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            IglooPieces.func_207617_a(templateManagerIn, blockpos, rotation, this.components, this.rand, nofeatureconfig);
            this.recalculateStructureSize();
        }
    }
}

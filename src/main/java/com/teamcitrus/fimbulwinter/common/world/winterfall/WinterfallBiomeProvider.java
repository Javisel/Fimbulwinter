package com.teamcitrus.fimbulwinter.common.world.winterfall;

import com.google.common.collect.Sets;
import com.teamcitrus.fimbulwinter.common.registration.BiomeRegistration;
import com.teamcitrus.fimbulwinter.common.world.winterfall.biome.FrigidForests;
import com.teamcitrus.fimbulwinter.common.world.winterfall.biome.FrozenOceans;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProviderSettings;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.util.*;

public class WinterfallBiomeProvider extends BiomeProvider {
    private final SimplexNoiseGenerator generator;
    private final SharedSeedRandom random;
    private final Biome[] biomes = new Biome[]{BiomeRegistration.PermafrostPlains,BiomeRegistration.FrigidForests,BiomeRegistration.FrozenOcean,BiomeRegistration.HollowMountains,BiomeRegistration.FreezingRiver};
    public WinterfallBiomeProvider() {

        EndBiomeProviderSettings endBiomeProviderSettings = new EndBiomeProviderSettings();

        this.random = new SharedSeedRandom((endBiomeProviderSettings.getSeed()));
        this.random.skip(17292);
        this.generator = new SimplexNoiseGenerator(this.random);
    }

    /**
     * Gets the biome from the provided coordinates
     */
    public Biome getBiome(int x, int y) {
        int i = x >> 4;
        int j = y >> 4;
        Long l = (long)i * (long)i + (long)j * (long)j;

        if (l <= 2000) {
            return BiomeRegistration.PermafrostPlains;
        } else {

            if (l<=3000) {

                return BiomeRegistration.FrigidForests;
            }



        }
        return BiomeRegistration.FrozenOcean;
    }

    public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
        Biome[] abiome = new Biome[width * length];
        Long2ObjectMap<Biome> long2objectmap = new Long2ObjectOpenHashMap<>();

        for(int i = 0; i < width; ++i) {
            for(int j = 0; j < length; ++j) {
                int k = i + x;
                int l = j + z;
                long i1 = ChunkPos.asLong(k, l);
                Biome biome = long2objectmap.get(i1);
                if (biome == null) {
                    biome = this.getBiome(k, l);
                    long2objectmap.put(i1, biome);
                }

                abiome[i + j * width] = biome;
            }
        }

        return abiome;
    }

    public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
        int i = centerX - sideLength >> 2;
        int j = centerZ - sideLength >> 2;
        int k = centerX + sideLength >> 2;
        int l = centerZ + sideLength >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        return Sets.newHashSet(this.getBiomeBlock(i, j, i1, j1));
    }

    @Nullable
    public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
        int i = x - range >> 2;
        int j = z - range >> 2;
        int k = x + range >> 2;
        int l = z + range >> 2;
        int i1 = k - i + 1;
        int j1 = l - j + 1;
        Biome[] abiome = this.getBiomeBlock(i, j, i1, j1);
        BlockPos blockpos = null;
        int k1 = 0;

        for(int l1 = 0; l1 < i1 * j1; ++l1) {
            int i2 = i + l1 % i1 << 2;
            int j2 = j + l1 / i1 << 2;
            if (biomes.contains(abiome[l1])) {
                if (blockpos == null || random.nextInt(k1 + 1) == 0) {
                    blockpos = new BlockPos(i2, 0, j2);
                }

                ++k1;
            }
        }

        return blockpos;
    }

    public float func_222365_c(int p_222365_1_, int p_222365_2_) {
        int i = p_222365_1_ / 2;
        int j = p_222365_2_ / 2;
        int k = p_222365_1_ % 2;
        int l = p_222365_2_ % 2;
        float f = 100.0F - MathHelper.sqrt((float)(p_222365_1_ * p_222365_1_ + p_222365_2_ * p_222365_2_)) * 8.0F;
        f = MathHelper.clamp(f, -100.0F, 80.0F);

        for(int i1 = -12; i1 <= 12; ++i1) {
            for(int j1 = -12; j1 <= 12; ++j1) {
                long k1 = (long)(i + i1);
                long l1 = (long)(j + j1);
                if (k1 * k1 + l1 * l1 > 4096L && this.generator.getValue((double)k1, (double)l1) < (double)-0.9F) {
                    float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
                    float f2 = (float)(k - i1 * 2);
                    float f3 = (float)(l - j1 * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
                    f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
                    f = Math.max(f, f4);
                }
            }
        }

        return f;
    }

    public boolean hasStructure(Structure<?> structureIn) {
        return this.hasStructureCache.computeIfAbsent(structureIn, (p_205008_1_) -> {
            for(Biome biome : this.biomes) {
                if (biome.hasStructure(p_205008_1_)) {
                    return true;
                }
            }

            return false;
        });
    }

    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for(Biome biome : this.biomes) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            }
        }

        return this.topBlocksCache;
    }
}
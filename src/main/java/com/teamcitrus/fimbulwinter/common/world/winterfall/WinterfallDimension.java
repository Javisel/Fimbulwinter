package com.teamcitrus.fimbulwinter.common.world.winterfall;

import com.teamcitrus.fimbulwinter.common.registration.DimensionRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public class WinterfallDimension extends Dimension {

    private WinterfallGenSettings winterfallGenSettings;

    public WinterfallDimension(World world, DimensionType dimensionType) {
        super(world,dimensionType);
        winterfallGenSettings = new WinterfallGenSettings();

    }

    @Override
    public WinterfallChunkGenerator createChunkGenerator() {



        return  new WinterfallChunkGenerator(world,new WinterfallBiomeProvider(),winterfallGenSettings);
    }
    @Override
    public World getWorld() {

        return this.world;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        return new BlockPos(0,150,0);
    }


    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(posX, 0, posZ);
        Biome biome = this.world.getBiome(blockpos$mutableblockpos);
        BlockState blockstate = biome.getSurfaceBuilderConfig().getTop();
        if (checkValid && !blockstate.getBlock().isIn(BlockTags.VALID_SPAWN)) {
            return null;
        } else {
            Chunk chunk = this.world.getChunk(posX >> 4, posZ >> 4);
            int i = chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, posX & 15, posZ & 15);
            if (i < 0) {
                return null;
            } else if (chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, posX & 15, posZ & 15) > chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, posX & 15, posZ & 15)) {
                return null;
            } else {
                for(int j = i + 1; j >= 0; --j) {
                    blockpos$mutableblockpos.setPos(posX, j, posZ);
                    BlockState blockstate1 = this.world.getBlockState(blockpos$mutableblockpos);
                    if (!blockstate1.getFluidState().isEmpty()) {
                        break;
                    }

                    if (blockstate1.equals(blockstate)) {
                        return blockpos$mutableblockpos.up().toImmutable();
                    }
                }

                return null;
            }
        }
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d(4,4,4);
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }

    @Override
    public Dimension getDimension() {
        return this;
    }

    @Override
    public ICapabilityProvider initCapabilities() {
        return super.initCapabilities();
    }

    @Override
    public double getMovementFactor() {
        return 1;
    }

    @Override
    public void getLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors) {

    }

    @Override
    public boolean canDoLightning(Chunk chunk) {
        return true;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk) {
        return true;
    }

    @Nullable
    @Override
    public MusicTicker.MusicType getMusicType() {
        return MusicTicker.MusicType.END;
    }

    @Override
    public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
        return SleepResult.DENY;
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return super.getBiome(pos);
    }

    @Override
    public boolean isDaytime() {
        return false;
    }

    @Override
    public float getSunBrightness(float partialTicks) {
        return 0;
    }

    @Override
    public Vec3d getSkyColor(BlockPos cameraPos, float partialTicks) {
        return new Vec3d(0,0,0);
    }

    @Override
    public Vec3d getCloudColor(float partialTicks) {
        return new Vec3d(0,0,0);
    }

    @Override
    public float getCurrentMoonPhaseFactor(long time) {
        return 0;
    }

    @Override
    public float getStarBrightness(float partialTicks) {
        return 50;
    }

    @Override
    public void setAllowedSpawnTypes(boolean allowHostile, boolean allowPeaceful) {


    }




    @Override
    public long getWorldTime() {
        return 0;
    }

    @Override
    public void setWorldTime(long time) {

    }

    @Override
    public BlockPos getSpawnPoint() {
        return new BlockPos(0,150,0);
    }

    @Override
    public void setSpawnPoint(BlockPos pos) {

    }

    @Override
    public boolean canMineBlock(PlayerEntity player, BlockPos pos) {
        return super.canMineBlock(player,pos);
    }

    @Override
    public boolean isHighHumidity(BlockPos pos) {
        return false;
    }

    @Override
    public int getHeight() {
        return 256;
    }

    @Override
    public int getActualHeight() {
        return 256;
    }

    @Override
    public double getHorizon() {
        return 0;
    }

    @Override
    public int getSeaLevel() {
        return 60;
    }

    @Override
    public boolean shouldMapSpin(String entity, double x, double z, double rotation) {
        return true;
    }

    @Override
    public DimensionType getRespawnDimension(ServerPlayerEntity player) {
        return DimensionRegistration.WINTERFALL_TYPE;
    }
}

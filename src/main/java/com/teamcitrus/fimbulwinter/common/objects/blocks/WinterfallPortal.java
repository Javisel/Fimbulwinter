package com.teamcitrus.fimbulwinter.common.objects.blocks;

import com.teamcitrus.fimbulwinter.common.registration.BlockRegistration;
import com.teamcitrus.fimbulwinter.common.registration.DimensionRegistration;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Random;

public class WinterfallPortal extends BlockBase {


    public WinterfallPortal() {
        super(Properties.create(Material.SNOW_BLOCK).hardnessAndResistance(5,5).harvestTool(ToolType.SHOVEL).lightValue(15).tickRandomly(), "winterfall_portal");

    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {

        if (entityIn instanceof ServerPlayerEntity && entityIn.timeUntilPortal==0 ) {



                ServerPlayerEntity player = (ServerPlayerEntity) entityIn;

                if (player.isSleeping()) {


                    player.wakeUp();

                }
                    teleportPlayer(player,player.getPosition());






        }

    }

    @Override
    public int tickRate(IWorldReader worldIn) {
        return 0;
    }
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(100) == 0) {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 25; ++i) {
            double d0 = (float)pos.getX() + rand.nextFloat();
            double d1 = (float)pos.getY() + rand.nextFloat();
            double d2 = (float)pos.getZ() + rand.nextFloat();
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            worldIn.addParticle(new BlockParticleData(ParticleTypes.FALLING_DUST,this.getDefaultState()), d0, pos.getY()+1, d2, d3, -3, d5);
        }

    }



    public static void teleportPlayer(ServerPlayerEntity player, BlockPos destinationPos)
    {
        ServerWorld nextWorld = player.getServer().getWorld(player.dimension ==DimensionRegistration.WINTERFALL_TYPE ? DimensionType.OVERWORLD : DimensionRegistration.WINTERFALL_TYPE);
        nextWorld.getChunk(destinationPos);    // make sure the chunk is loaded
        player.teleport(nextWorld, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), player.rotationYaw, player.rotationPitch);

        if (player.getPosition().getY()<=2) {

            player.setPositionAndUpdate(player.getPosition().getX(),player.getPosition().getY()+1,player.getPosition().getZ());
        }
        for (int x = -1;x <2; x++) {

            for (int y = 0;y <2; y++) {


                for (int z = -1; z < 2; z++) {

                    nextWorld.removeBlock(player.getPosition().add(x, y, z), false);

                }
            }
        }


        nextWorld.removeBlock(player.getPosition(),false);
        nextWorld.removeBlock(player.getPosition().up(),false);
        nextWorld.setBlockState(player.getPosition().down(),BlockRegistration.WINTERFALL_PORTAL.getDefaultState());


        player.timeUntilPortal=120;

    }
}

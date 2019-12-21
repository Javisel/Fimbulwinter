package com.teamcitrus.fimbulwinter.common.objects.blocks;

import com.teamcitrus.fimbulwinter.common.objects.entities.Sentinel;
import com.teamcitrus.fimbulwinter.common.registration.ItemRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class DormantSentinel extends BlockBase {


    public DormantSentinel() {
        super(Properties.create(Material.SNOW_BLOCK).hardnessAndResistance(3,-1F).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE),"dormant_sentinel");


    }


}

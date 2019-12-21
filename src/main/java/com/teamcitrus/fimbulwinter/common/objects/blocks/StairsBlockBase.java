package com.teamcitrus.fimbulwinter.common.objects.blocks;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

import java.util.function.Supplier;

public class StairsBlockBase extends StairsBlock {

    public StairsBlockBase(String name, Supplier<BlockState> p_i51807_1_, Properties p_i51807_2_) {
        super(p_i51807_1_, p_i51807_2_);
        setRegistryName(Fimbulwinter.NAME,name);
    }
}

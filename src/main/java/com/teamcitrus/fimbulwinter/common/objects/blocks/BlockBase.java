package com.teamcitrus.fimbulwinter.common.objects.blocks;

import net.minecraft.block.Block;

public class BlockBase extends Block {


    public BlockBase(Properties properties, String name) {
        super(properties);
        setRegistryName(name);


    }
}

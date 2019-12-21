package com.teamcitrus.fimbulwinter.common.objects.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class ItemBlock extends BlockItem {


    public ItemBlock(Block blockIn, Properties builder) {
        super(blockIn, builder);
        this.setRegistryName(blockIn.getRegistryName());
    }
}

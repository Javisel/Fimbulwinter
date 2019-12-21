package com.teamcitrus.fimbulwinter.common.objects.items;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name,Properties properties) {
        super(properties);
        setRegistryName(Fimbulwinter.MODID,name);
    }
}

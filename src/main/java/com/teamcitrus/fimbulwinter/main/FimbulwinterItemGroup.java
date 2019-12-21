package com.teamcitrus.fimbulwinter.main;

import com.teamcitrus.fimbulwinter.common.registration.ItemRegistration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FimbulwinterItemGroup extends ItemGroup {
    public FimbulwinterItemGroup() {
        super(Fimbulwinter.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemRegistration.GEHENNITE_KATANA, 1);
    }
}

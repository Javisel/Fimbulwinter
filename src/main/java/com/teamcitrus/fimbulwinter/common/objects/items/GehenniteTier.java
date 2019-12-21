package com.teamcitrus.fimbulwinter.common.objects.items;

import com.teamcitrus.fimbulwinter.common.registration.ItemRegistration;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public class GehenniteTier implements IItemTier {
    @Override
    public int getMaxUses() {
        return 1245;
    }

    @Override
    public float getEfficiency() {
        return 2;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getHarvestLevel() {
        return 3;
    }

    @Override
    public int getEnchantability() {
        return 4;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(ItemRegistration.GEHENNITE_INGOT);
    }
}

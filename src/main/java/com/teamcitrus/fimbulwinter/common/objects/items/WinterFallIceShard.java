package com.teamcitrus.fimbulwinter.common.objects.items;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class WinterFallIceShard extends ItemBase {
    public WinterFallIceShard() {
        super("winterfall_ice_shard", new Properties().group(Fimbulwinter.fimbulwinterItemGroup).rarity(Rarity.RARE).maxStackSize(8));
    }



    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.fimbulwinter.winterfalliceshard"));
    }
}

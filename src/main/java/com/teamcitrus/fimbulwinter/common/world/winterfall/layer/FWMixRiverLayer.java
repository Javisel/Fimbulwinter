package com.teamcitrus.fimbulwinter.common.world.winterfall.layer;

import com.teamcitrus.fimbulwinter.common.registration.BiomeRegistration;
import com.teamcitrus.fimbulwinter.common.world.winterfall.WinterfallLayerGen;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum FWMixRiverLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    private static final int FREEZING_RIVER = Registry.BIOME.getId(BiomeRegistration.FreezingRiver);
    private static final int SNOWY_TUNDRA = Registry.BIOME.getId(Biomes.SNOWY_TUNDRA);


    public int apply(INoiseRandom p_215723_1_, IArea p_215723_2_, IArea p_215723_3_, int p_215723_4_, int p_215723_5_) {
        int i = p_215723_2_.getValue(this.func_215721_a(p_215723_4_), this.func_215722_b(p_215723_5_));
        int j = p_215723_3_.getValue(this.func_215721_a(p_215723_4_), this.func_215722_b(p_215723_5_));
        if (WinterfallLayerGen.isOcean(i)) {
            return i;
        } else if (j == FREEZING_RIVER) {
            return Registry.BIOME.getId(Registry.BIOME.getByValue(i).getRiver());
        } else {
            return i;
        }
    }
}
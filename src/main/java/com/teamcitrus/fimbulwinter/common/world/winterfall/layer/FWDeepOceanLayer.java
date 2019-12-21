package com.teamcitrus.fimbulwinter.common.world.winterfall.layer;

import com.teamcitrus.fimbulwinter.common.world.winterfall.WinterfallLayerGen;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum FWDeepOceanLayer implements ICastleTransformer {
    INSTANCE;

    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        if (WinterfallLayerGen.isShallowOcean(center)) {
            int i = 0;
            if (WinterfallLayerGen.isShallowOcean(north)) {
                ++i;
            }

            if (WinterfallLayerGen.isShallowOcean(west)) {
                ++i;
            }

            if (WinterfallLayerGen.isShallowOcean(east)) {
                ++i;
            }

            if (WinterfallLayerGen.isShallowOcean(south)) {
                ++i;
            }

            if (i > 3) {
                if (center == WinterfallLayerGen.COLD_OCEAN) {
                    return WinterfallLayerGen.DEEP_COLD_OCEAN;
                }

                if (center == WinterfallLayerGen.FROZEN_OCEAN) {
                    return WinterfallLayerGen.DEEP_FROZEN_OCEAN;
                }


                return WinterfallLayerGen.DEEP_FROZEN_OCEAN;
            }
        }

        return center;
    }
}
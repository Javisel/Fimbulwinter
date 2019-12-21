package com.teamcitrus.fimbulwinter.common.world.winterfall;

import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;

import java.util.function.BiFunction;

public class WinterfallModDimension extends ModDimension {
    public WinterfallModDimension() {
        super();

        setRegistryName(Fimbulwinter.WINTERFALLLOCATION);
    }

    @Override
    public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
       return WinterfallDimension::new;
    }
}

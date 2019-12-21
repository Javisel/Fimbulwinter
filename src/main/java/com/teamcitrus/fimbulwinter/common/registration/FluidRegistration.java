package com.teamcitrus.fimbulwinter.common.registration;

import com.teamcitrus.fimbulwinter.common.objects.blocks.*;
import com.teamcitrus.fimbulwinter.common.objects.fluids.FreezingWater;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Fimbulwinter.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class FluidRegistration {

    public static Fluid FREEZING_WATER = null;
    public static FlowingFluid FLOWING_FREEZING_WATER = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Fluid> event) {
        event.getRegistry().registerAll
                (


                );
    }


}

package com.teamcitrus.fimbulwinter.common.registration;


import com.teamcitrus.fimbulwinter.common.world.winterfall.WinterfallModDimension;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Fimbulwinter.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DimensionRegistration {


    public static final ModDimension WINTERFALL = null;
    public static DimensionType WINTERFALL_TYPE;

    @Mod.EventBusSubscriber(modid = Fimbulwinter.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModRegistry {
        @SubscribeEvent
        public static void registerModDimensions(RegistryEvent.Register<ModDimension> event) {
            event.getRegistry().register(new WinterfallModDimension());
        }
    }

    @Mod.EventBusSubscriber(modid = Fimbulwinter.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeRegistry {
        @SubscribeEvent
        public static void registerDimensions(RegisterDimensionsEvent event) {

            WINTERFALL_TYPE = DimensionManager.registerOrGetDimension(Fimbulwinter.WINTERFALLLOCATION, WINTERFALL, null, false);

        }
    }
}





package com.teamcitrus.fimbulwinter.common.registration;

import com.teamcitrus.fimbulwinter.common.world.winterfall.biome.*;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Fimbulwinter.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeRegistration {

    public static Biome FrigidForests = null;
    public static Biome FrozenOcean = null;
    public static Biome HollowMountains = null;
    public static Biome PermafrostPlains = null;
    public static Biome FreezingRiver = null;

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {


        event.getRegistry().registerAll
                (
                    FrigidForests = new FrigidForests(),

                    FrozenOcean = new FrozenOceans(),
                        HollowMountains = new HollowMountains(),
                        PermafrostPlains = new PermafrostPlains(),
                        FreezingRiver = new FreezingRiver()

                );
    }

    public static  void  registerDictionary() {
        BiomeDictionary.addTypes(FrigidForests, BiomeDictionary.Type.COLD, BiomeDictionary.Type.FOREST);
        BiomeDictionary.addTypes(FrozenOcean, BiomeDictionary.Type.COLD, BiomeDictionary.Type.OCEAN);
        BiomeDictionary.addTypes(HollowMountains, BiomeDictionary.Type.COLD, BiomeDictionary.Type.MOUNTAIN);
        BiomeDictionary.addTypes(PermafrostPlains, BiomeDictionary.Type.COLD, BiomeDictionary.Type.PLAINS);
        BiomeDictionary.addTypes(FreezingRiver, BiomeDictionary.Type.COLD, BiomeDictionary.Type.RIVER);



    }



}

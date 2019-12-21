package com.teamcitrus.fimbulwinter.common.registration;

import com.teamcitrus.fimbulwinter.common.objects.items.*;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Fimbulwinter.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegistration {

    public static GehenniteKatana GEHENNITE_KATANA = null;
    public static GehenniteHammer GEHENNITE_HAMMER = null;
    //public static Frostbow FROST_BOW = null;
    public static Item PURE_SNOWFLAKE = null;
    public static Item WINTERFALL_ICE_SHARD = null;
    public static Item GEHENNITE_INGOT = null;
    @SubscribeEvent
    public static void registerItem(final RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(

                GEHENNITE_KATANA = new GehenniteKatana(),
                GEHENNITE_HAMMER = new GehenniteHammer(),
               // FROST_BOW = new Frostbow(),
                PURE_SNOWFLAKE = new PureSnowflake(),
                WINTERFALL_ICE_SHARD = new WinterFallIceShard(),
                GEHENNITE_INGOT = new ItemBase("gehennite_ingot",new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup))
        );


    }


}

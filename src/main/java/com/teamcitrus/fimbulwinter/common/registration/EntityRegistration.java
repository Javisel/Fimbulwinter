package com.teamcitrus.fimbulwinter.common.registration;

import com.teamcitrus.fimbulwinter.common.objects.entities.*;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)

public class EntityRegistration  {


    public static EntityType<?> FROZEN_ZOMBIE = EntityType.Builder.create(FrozenZombie::new, EntityClassification.MONSTER).build(Fimbulwinter.MODID + ":frozen_zombie").setRegistryName(Fimbulwinter.MODID,"frozen_zombie");
    public static EntityType<?> FROZEN_SPIDER = EntityType.Builder.create(FrozenSpider::new, EntityClassification.MONSTER).size(1.4F, 0.9F).build(Fimbulwinter.MODID + ":frozen_spider").setRegistryName(Fimbulwinter.MODID,"frozen_spider");
    public static EntityType<?> FROZEN_CREEPER = EntityType.Builder.create(FrozenCreeper::new, EntityClassification.MONSTER).size(1.4F, 0.9F).build(Fimbulwinter.MODID + ":frozen_creeper").setRegistryName(Fimbulwinter.MODID,"frozen_creeper");

    public static EntityType<?> SENTINEL = EntityType.Builder.create(Sentinel::new,EntityClassification.MONSTER).size(1.4F,2.7F).build(Fimbulwinter.MODID +":sentinel").setRegistryName(Fimbulwinter.MODID, "sentinel");
    public static EntityType<?> ICE_CRYSTAL = EntityType.Builder.create(IceCrystal::new,EntityClassification.MISC).size(1F,1F).build(Fimbulwinter.MODID +":ice_crystal").setRegistryName(Fimbulwinter.MODID, "ice_crystal");


    public static Item FROZEN_ZOMBIE_SPAWN_EGG = null;
    public static Item FROZEN_SPIDER_SPAWN_EGG = null;
    public static Item FROZEN_CREEPER_SPAWN_EGG = null;

    @SubscribeEvent
    public static void registerEntitySpawnEggs(final RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll
                (
                        FROZEN_ZOMBIE_SPAWN_EGG= registerEntitySpawnEgg(FROZEN_ZOMBIE, 013220, 240*248*255, "frozen_zombie_egg"),
                        FROZEN_SPIDER_SPAWN_EGG= registerEntitySpawnEgg(FROZEN_SPIDER, 55, 240*248*255, "frozen_spider_egg"),
                        FROZEN_CREEPER_SPAWN_EGG = registerEntitySpawnEgg(FROZEN_CREEPER,55,233,"frozen_creeper_egg")
                );

    }
    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup));
        item.setRegistryName(name);
        return item;
    }


    @SubscribeEvent
    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().registerAll
                (

                        FROZEN_ZOMBIE,
                        FROZEN_SPIDER,
                        FROZEN_CREEPER,
                        SENTINEL,
                        ICE_CRYSTAL
                );


    }


}

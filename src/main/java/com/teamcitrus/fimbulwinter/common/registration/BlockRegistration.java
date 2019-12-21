package com.teamcitrus.fimbulwinter.common.registration;


import com.teamcitrus.fimbulwinter.common.objects.blocks.*;
import com.teamcitrus.fimbulwinter.common.objects.items.ItemBlock;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(Fimbulwinter.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistration {

    public static FrozenCobWeb FROZEN_COBWEB = null;
    public static WinterfallPortal WINTERFALL_PORTAL = null;
    public static BlockBase SNOW_BRICKS = null;
    public static BlockBase DORMANT_SENTINEL = null;
    public static FallingBlock SOFT_SNOW = null;
    public static FallingBlock FREEZING_SNOW = null;
    public static StairsBlock SNOW_BRICK_STAIRS = null;
    public static SlabBase SNOW_BRICK_SLABS = null;
    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll
                (
                        FROZEN_COBWEB = new FrozenCobWeb(),
                        WINTERFALL_PORTAL = new WinterfallPortal(),
                        SNOW_BRICKS = new BlockBase(Block.Properties.create(Material.SNOW_BLOCK).hardnessAndResistance(3,3).harvestTool(ToolType.PICKAXE),"snow_bricks"),
                        DORMANT_SENTINEL = new DormantSentinel(),
                        SOFT_SNOW = new SoftSnow(),
                        FREEZING_SNOW = new FreezingSnow(),
                        SNOW_BRICK_STAIRS = new StairsBlockBase("snow_brick_stairs",new Supplier<BlockState>() {
                            @Override
                            public BlockState get() {
                                return BlockRegistration.SNOW_BRICKS.getDefaultState();
                            }
                        },Block.Properties.create(Material.SNOW_BLOCK, MaterialColor.WHITE_TERRACOTTA).hardnessAndResistance(3, 3).harvestTool(ToolType.PICKAXE)),
                        SNOW_BRICK_SLABS = new SlabBase("snow_brick_slab", Block.Properties.create(Material.SNOW_BLOCK).hardnessAndResistance(3,3).harvestTool(ToolType.PICKAXE))
                );
    }

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(

                new ItemBlock(FROZEN_COBWEB, new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup)),
                new ItemBlock(WINTERFALL_PORTAL,new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup)),
                new ItemBlock(SNOW_BRICKS,new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup)),
                new ItemBlock(DORMANT_SENTINEL,new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup)),
                new ItemBlock(SOFT_SNOW, new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup)),
                new ItemBlock(FREEZING_SNOW, new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup)),
        new ItemBlock(SNOW_BRICK_STAIRS ,new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup)),
                new ItemBlock(SNOW_BRICK_SLABS ,new Item.Properties().group(Fimbulwinter.fimbulwinterItemGroup))

        );


    }


}

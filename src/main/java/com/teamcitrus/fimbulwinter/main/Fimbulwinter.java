package com.teamcitrus.fimbulwinter.main;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.biome.Biomes;
import org.apache.commons.lang3.tuple.Pair;
import com.teamcitrus.fimbulwinter.client.overlay.GameOverlayHandler;
import com.teamcitrus.fimbulwinter.client.renderer.RenderRegistration;
import com.teamcitrus.fimbulwinter.common.capabilities.*;
import com.teamcitrus.fimbulwinter.common.objects.items.GehenniteTier;
import com.teamcitrus.fimbulwinter.common.registration.CapabilityRegistration;
import com.teamcitrus.fimbulwinter.common.registration.EntityRegistration;
import com.teamcitrus.fimbulwinter.common.registration.PacketRegistration;
import com.teamcitrus.fimbulwinter.proxy.ClientProxy;
import com.teamcitrus.fimbulwinter.proxy.IProxy;
import com.teamcitrus.fimbulwinter.proxy.ServerProxy;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityClassification;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLHandshakeMessages;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import static com.teamcitrus.fimbulwinter.common.registration.DimensionRegistration.WINTERFALL_TYPE;
import static net.minecraft.entity.SharedMonsterAttributes.*;


@Mod("fimbulwinter")
public class Fimbulwinter {

    public static final String MODID = "fimbulwinter", NAME = "Fimbulwinter", VERSION = "1.0.0";
    public static final GehenniteTier GEHENNITE_TIER = new GehenniteTier();
    public static final String SWINGAMOUNT = "swingamount";
    private static final Logger LOGGER = LogManager.getLogger();
    public static ResourceLocation WINTERFALLLOCATION = new ResourceLocation(MODID,"winterfall");
    public static FimbulwinterItemGroup fimbulwinterItemGroup = new FimbulwinterItemGroup();
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Fimbulwinter() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        });


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        MinecraftForge.EVENT_BUS.register(new CapabilityRegistration());

    }

    private void setup(final FMLCommonSetupEvent event) {


        CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataStorage(), PlayerData::new);
        CapabilityManager.INSTANCE.register(IArrowData.class,new ArrowDataStorage(),ArrowData::new);
        PacketRegistration.register();
        disablebiomes();


    }





    private void disablebiomes() {




        Iterator destroybiome3 = BiomeManager.getBiomes(BiomeManager.BiomeType.ICY).iterator();
        while (destroybiome3.hasNext()) {

            BiomeManager.BiomeEntry biomeEntry = (BiomeManager.BiomeEntry) destroybiome3.next();



            biomeEntry.biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(EntityRegistration.FROZEN_ZOMBIE,100,1,2));
            biomeEntry.biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(EntityRegistration.FROZEN_SPIDER,100,1,3));
            biomeEntry.biome.getSpawns(EntityClassification.MONSTER).add(new Biome.SpawnListEntry(EntityRegistration.FROZEN_CREEPER,100,1,3));





        }




    }




    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new GameOverlayHandler());

        RenderRegistration.registryEntityRenders();

    }




}

package com.teamcitrus.fimbulwinter.common.registration;

import com.teamcitrus.fimbulwinter.common.capabilities.ArrowData;
import com.teamcitrus.fimbulwinter.common.capabilities.ArrowDataProvider;
import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistration {


    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {

        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Fimbulwinter.MODID, "playerdata"), new PlayerDataProvider());
        }


    }


}
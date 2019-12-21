package com.teamcitrus.fimbulwinter.main;

import com.teamcitrus.fimbulwinter.common.capabilities.ArrowDataProvider;
import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import com.teamcitrus.fimbulwinter.common.capabilities.IPlayerData;
import com.teamcitrus.fimbulwinter.common.network.HeatMessage;
import com.teamcitrus.fimbulwinter.common.objects.entities.Sentinel;
import com.teamcitrus.fimbulwinter.common.objects.items.FimbulwinterBow;
import com.teamcitrus.fimbulwinter.common.registration.EffectRegistration;
import com.teamcitrus.fimbulwinter.common.registration.PacketRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class ServerEvents {


    @SubscribeEvent
    public void vulnerability(LivingHurtEvent e) {

        if (e.getEntityLiving().getActivePotionEffect(EffectRegistration.VULNERABILITY) !=null) {

            float newamount = (float) (e.getAmount() + ((e.getAmount() * (0.10 + (0.10 * e.getEntityLiving().getActivePotionEffect(EffectRegistration.VULNERABILITY).getAmplifier())))));

            e.setAmount(newamount);

        }


    }






    @SubscribeEvent
    public void dataSync(PlayerEvent.PlayerLoggedInEvent e) {


        IPlayerData entityData = e.getPlayer().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new);

        PacketRegistration.HANDLER.sendTo(new HeatMessage(entityData.saveNBTData()), ((ServerPlayerEntity) e.getPlayer()).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);


    }
    @SubscribeEvent
    public void attack(LivingHurtEvent e) {


        if (e.getSource().isFireDamage() && e.getEntityLiving().getActivePotionEffect(EffectRegistration.PheonixFlame) !=null) {
            e.setCanceled(true);

        }

    }




    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void pheonixRessurrection(LivingDamageEvent e) {


        if (e.getEntityLiving().getActivePotionEffect(EffectRegistration.PheonixFlame) != null && e.getAmount() >= e.getEntityLiving().getHealth()) {

            e.getEntityLiving().setHealth((float) (e.getEntityLiving().getHealth() * .25));

            e.setCanceled(true);

            e.getEntityLiving().removeActivePotionEffect(EffectRegistration.PheonixFlame);
            e.getEntityLiving().addPotionEffect(new EffectInstance(Effects.REGENERATION, 600, 0));

        }

    }







    @SubscribeEvent
    public void playertick(TickEvent.PlayerTickEvent e) {


        if (!e.player.world.isRemote && e.player.isAlive() && e.phase == TickEvent.Phase.START) {

            e.player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new).tick();
            IPlayerData entityData = e.player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new);

            PacketRegistration.HANDLER.sendTo(new HeatMessage(entityData.saveNBTData()), ((ServerPlayerEntity) e.player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }

    }


    @SubscribeEvent
    public void damageEvent(LivingDamageEvent e) {



        if (e.getSource().getTrueSource() != null) {

            if (e.getEntityLiving() instanceof PlayerEntity) {

                IPlayerData data = e.getEntityLiving().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new);
                data.setCurrentEntropy(data.getstartEntropyTime());


            }

            if (e.getSource().getTrueSource() instanceof PlayerEntity) {
                IPlayerData data = e.getSource().getTrueSource().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new);
                data.setCurrentEntropy(data.getstartEntropyTime());

            }

        }

    }


}

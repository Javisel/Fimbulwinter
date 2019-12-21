package com.teamcitrus.fimbulwinter.main;

import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import com.teamcitrus.fimbulwinter.common.capabilities.IPlayerData;
import com.teamcitrus.fimbulwinter.common.network.HeatMessage;
import com.teamcitrus.fimbulwinter.common.registration.PacketRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;

public class Utilities {


    public static void addHeat(PlayerEntity playerEntity, double amount) {

        IPlayerData entityData = playerEntity.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new);

        entityData.addHeat(amount);
        PacketRegistration.HANDLER.sendTo(new HeatMessage(entityData.saveNBTData()), ((ServerPlayerEntity) playerEntity).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);


    }


}

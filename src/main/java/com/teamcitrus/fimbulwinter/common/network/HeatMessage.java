package com.teamcitrus.fimbulwinter.common.network;

import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HeatMessage {

    public static CompoundNBT nbt;


    public HeatMessage(CompoundNBT nbtag) {

        nbt = nbtag;

    }


    public static void encode(HeatMessage pkt, PacketBuffer buf) {
        buf.writeCompoundTag(nbt);
    }

    public static HeatMessage decode(PacketBuffer buf) {
        return new HeatMessage(buf.readCompoundTag());
    }


    public static class Handler {

        public static void handle(final HeatMessage mes, Supplier<NetworkEvent.Context> ctx) {


            ctx.get().enqueueWork(() -> {

                Minecraft minecraft = Minecraft.getInstance();
                minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new).loadNBTData(mes.nbt);

            });

            ctx.get().setPacketHandled(true);

        }
    }

}

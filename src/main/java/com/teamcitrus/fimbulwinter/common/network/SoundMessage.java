package com.teamcitrus.fimbulwinter.common.network;

import com.teamcitrus.fimbulwinter.common.capabilities.PlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.function.Supplier;

public class SoundMessage {

    public static CompoundNBT nbt;


    public SoundMessage(CompoundNBT nbtag) {

        nbt = nbtag;

    }


    public static void encode(SoundMessage pkt, PacketBuffer buf) {
        buf.writeCompoundTag(nbt);
    }

    public static SoundMessage decode(PacketBuffer buf) {
        return new SoundMessage(buf.readCompoundTag());
    }


    public static class Handler {

        public static void handle(final SoundMessage mes, Supplier<NetworkEvent.Context> ctx) {


            ctx.get().enqueueWork(() -> {

                Minecraft minecraft = Minecraft.getInstance();
                minecraft.player.getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null).orElseThrow(NullPointerException::new).loadNBTData(mes.nbt);

            });

            ctx.get().setPacketHandled(true);

        }
    }
}

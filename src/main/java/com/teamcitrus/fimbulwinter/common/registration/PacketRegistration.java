package com.teamcitrus.fimbulwinter.common.registration;

import com.teamcitrus.fimbulwinter.common.network.HeatMessage;
import com.teamcitrus.fimbulwinter.common.network.SoundMessage;
import com.teamcitrus.fimbulwinter.main.Fimbulwinter;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class PacketRegistration {

    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final SimpleChannel HANDLER = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Fimbulwinter.MODID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    private static int index;

    public static void register() {
        registerMessage(HeatMessage.class, HeatMessage::encode, HeatMessage::decode, HeatMessage.Handler::handle);
        registerMessage(SoundMessage.class, SoundMessage::encode,SoundMessage::decode,SoundMessage.Handler::handle);
    }

    private static <MSG> void registerMessage(Class<MSG> type, BiConsumer<MSG, PacketBuffer> encoder, Function<PacketBuffer, MSG> decoder,
                                              BiConsumer<MSG, Supplier<Context>> consumer) {
        HANDLER.registerMessage(index++, type, encoder, decoder, consumer);
    }

}

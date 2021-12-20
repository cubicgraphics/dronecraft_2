package net.cubic.dronecraft_2.dronecraft_2.Utill.network;

import net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer.PacketSetScannerRange;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer.PacketToggleScannerMode;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class PacketHandler {


    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation("dronecraft_2", "dronecraft_2"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);



    public PacketHandler(){
        MinecraftForge.EVENT_BUS.register(new WorldGlobalVar(this));
        this.addNetworkMessage(PacketToggleScannerMode.class,PacketToggleScannerMode::toBytes, PacketToggleScannerMode::new, PacketToggleScannerMode::handle);
        this.addNetworkMessage(PacketSetScannerRange.class,PacketSetScannerRange::toBytes, PacketSetScannerRange::new, PacketSetScannerRange::handle);
    }

    private int messageID = 0;
    public <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder,
                                      BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }


    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        PACKET_HANDLER.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }


    public static void sendToServer(Object packet) {
        PACKET_HANDLER.sendToServer(packet);
    }


}

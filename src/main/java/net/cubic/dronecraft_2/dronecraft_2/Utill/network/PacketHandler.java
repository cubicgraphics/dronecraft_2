package net.cubic.dronecraft_2.dronecraft_2.Utill.network;

import net.cubic.dronecraft_2.dronecraft_2.Utill.network.packets.to_client.SyncScannerAreaCapability;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


public class PacketHandler {

    private static SimpleChannel INSTANCE;
    private static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(dronecraft_2Main.MOD_ID, "dronecraft_2channel"),
                () -> "1.0",
                s -> true,
                s -> true);
/*
        INSTANCE.messageBuilder(SyncScannerAreaCapability.class, nextID())
                .encoder((packetOpenGui, packetBuffer) -> {})
                .decoder(buf -> new SyncScannerAreaCapability())
                .consumer(SyncScannerAreaCapability::handle)
                .add();
*/

        INSTANCE.messageBuilder(SyncScannerAreaCapability.class, nextID())
                .encoder(SyncScannerAreaCapability::toBytes)
                .decoder(SyncScannerAreaCapability::new)
                .consumer(SyncScannerAreaCapability::handle)
                .add();


    }

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }


    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}

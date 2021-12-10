package net.cubic.dronecraft_2.dronecraft_2.Utill.network;

import net.cubic.dronecraft_2.dronecraft_2.Utill.network.packets.IPEPacket;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.packets.to_client.SyncScannerAreaCapability;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Function;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(dronecraft_2Main.MOD_ID, "main_dronecraft_2"),
            () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

}

package net.cubic.dronecraft_2.dronecraft_2.Utill;

import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.CapabilityScannerArea;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.ScannerFormat;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ServerUtil {

    public static void SendToAllPlayers(String text){
        MinecraftServer mcServer = ServerLifecycleHooks.getCurrentServer();
        mcServer.getPlayerList().func_232641_a_(new StringTextComponent(text), ChatType.SYSTEM, Util.DUMMY_UUID);
    }
}

package net.cubic.dronecraft_2.dronecraft_2.Utill;

import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class ServerUtil {

    public static void SendToAllPlayers(String text){
        ServerLifecycleHooks.getCurrentServer().getPlayerList().func_232641_a_(new StringTextComponent(text), ChatType.SYSTEM, Util.DUMMY_UUID);
    }
}

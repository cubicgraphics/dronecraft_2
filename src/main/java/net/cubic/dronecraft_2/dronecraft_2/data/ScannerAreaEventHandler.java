package net.cubic.dronecraft_2.dronecraft_2.data;

import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;



@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ScannerAreaEventHandler {

    @SubscribeEvent
    public static void OnAttachCapabilitiesEvent(AttachCapabilitiesEvent<World> event){
        if (event.getObject() != null){
            ScannerAreaProvider provider = new ScannerAreaProvider();
            System.out.println("no longer crashes on next line");
            event.addCapability(new ResourceLocation(dronecraft_2Main.MOD_ID, "scannerareas"), provider);
            event.addListener(provider::invalidate);
        }
    }
}

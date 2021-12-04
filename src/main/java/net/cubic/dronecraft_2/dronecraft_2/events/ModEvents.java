package net.cubic.dronecraft_2.dronecraft_2.events;


import net.cubic.dronecraft_2.dronecraft_2.data.CapabilityScannerArea;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerFormat;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;




@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void ScanBlock(BlockEvent.BreakEvent  BlockEvent){
        /* This code was for testing purposes
        World world = (World) BlockEvent.getWorld();
        world.getCapability(CapabilityScannerArea.SCANNER_AREA).ifPresent(h-> {
            h.AddScanner(BlockEvent.getPos(),1);
            System.out.println("capabilitything working ?");
            System.out.println(h.GetScannerAreas().toString());

        });
        */
        System.out.println("blockupdt-" + BlockEvent.getPos().toString());
    }

}

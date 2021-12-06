package net.cubic.dronecraft_2.dronecraft_2.events;


import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;


@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void ScanBlock(BlockEvent.BreakEvent  BlockEvent){
        String name = BlockEvent.getPlayer().getDisplayName().toString();
        if (name == "noicdeppilf"){ //friend requested this
            Random rand = new Random();
            if (rand.nextInt(100) == 5){
                BlockEvent.setCanceled(true);
                BlockEvent.getPlayer().sendMessage(ITextComponent.getTextComponentOrEmpty("HA"),BlockEvent.getPlayer().getUniqueID()); // might work else may cause a crash idk
            }
        }
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

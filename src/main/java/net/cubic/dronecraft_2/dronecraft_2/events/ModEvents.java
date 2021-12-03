package net.cubic.dronecraft_2.dronecraft_2.events;


import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;




@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void ScanBlock(BlockEvent Event){
        System.out.println("blockupdt - dronectaft 2");
    }


}

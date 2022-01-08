package net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB;

import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class PCBEventHandler {

    @SubscribeEvent
    public static void OnAttachCapabilitiesEvent(AttachCapabilitiesEvent<ItemStack> event){
        if ((event.getObject() != null) && (event.getObject().getItem() == ModItems.PrintedCircuitBoard.get())){
            PCBProvider provider = new PCBProvider();
            System.out.println("adding pcb capability to item"); //why is this being called almost constantly when i touch an item
            event.addCapability(new ResourceLocation(dronecraft_2Main.MOD_ID, "pcb-item-data"), provider);
            event.addListener(provider::invalidate);
        }
    }
}

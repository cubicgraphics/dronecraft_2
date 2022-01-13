package net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPCB {

    @CapabilityInject(IPCB.class)
    public static Capability<IPCB> PCB_DATA = null;

    public static void register(){
        System.out.println("Dronecraft_2 registering PCBData capability");
        CapabilityManager.INSTANCE.register(IPCB.class, new storage(), DefaultPCB::new);
    }

    public static class storage implements Capability.IStorage<IPCB>{


        @Nullable
        @Override
        public INBT writeNBT(Capability<IPCB> capability, IPCB instance, Direction side) {
            //System.out.println("Writing Dronecraft PCB data to Itemstack");
            return instance.getPCBData().ToNBT();
        }

        @Override
        public void readNBT(Capability<IPCB> capability, IPCB instance, Direction side, INBT nbt) {
            //System.out.println("Reading Dronecraft PCB data from Itemstack");
            instance.setPCBData(PCBData.ReadNBT(nbt));
        }
    }
}

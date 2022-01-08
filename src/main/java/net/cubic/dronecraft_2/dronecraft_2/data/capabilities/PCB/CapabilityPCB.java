package net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
            CompoundNBT tag = new CompoundNBT();
            CompoundNBT PCBDATA = new CompoundNBT();
            PCBData pcbData = instance.getPCBData();
            ListNBT Wire2dArray = new ListNBT();
            if (pcbData != null) {
                PCBDATA.putInt("length", pcbData.length);
                PCBDATA.putInt("width", pcbData.width);
                //save the wires
                for (int i = 0; i < pcbData.PCBWiresArray.length; i++) {
                    CompoundNBT WireArray = new CompoundNBT();
                    WireArray.putIntArray("Wire",pcbData.PCBWiresArray[i]);
                    Wire2dArray.add(WireArray);
                }
                PCBDATA.put("2dWireArray", Wire2dArray);
                //save the components
                ListNBT Components = new ListNBT();
                for (int i = 0; i < pcbData.ComponentArray.length; i++) {
                    CompoundNBT Component = new CompoundNBT();
                    Component.putInt("ID",pcbData.ComponentArray[i].ComponentID);
                    Component.putInt("x",pcbData.ComponentArray[i].x);
                    Component.putInt("y",pcbData.ComponentArray[i].y);
                    Component.putBoolean("Built-In",pcbData.ComponentArray[i].BuiltInComponent);
                    Components.add(Component);
                }
                PCBDATA.put("Components", Components);
                tag.put("PCBData",PCBDATA);
            }
            return tag;
        }

        @Override
        public void readNBT(Capability<IPCB> capability, IPCB instance, Direction side, INBT nbt) {
            System.out.println("Reading Dronecraft PCB data from Itemstack");
            CompoundNBT pcbData = (CompoundNBT) ((CompoundNBT) nbt).get("PCBData");
            if (pcbData != null) {
                //read wire array
                int[][] intWireArray = new int[pcbData.getInt("length")][pcbData.getInt("width")];
                List<PCBComponentXY> ComponentArray = new ArrayList<>();
                ListNBT wireTagList = (ListNBT) (pcbData).get("2dWireArray");
                if(wireTagList != null){
                    CompoundNBT E = wireTagList.getCompound(0);
                    //intWireArray = new int[E.size()][E.getIntArray("Wire").length];
                    for (int i = 0; i < wireTagList.size(); i++) {
                        CompoundNBT WireArray = wireTagList.getCompound(i);
                        intWireArray[i] = WireArray.getIntArray("Wire");
                    }
                }
                //read component array
                ListNBT componentsTagList = (ListNBT) (pcbData).get("Components");
                if(componentsTagList != null){
                    for (int i = 0; i < componentsTagList.size(); i++) {
                        CompoundNBT Component = componentsTagList.getCompound(i);
                        ComponentArray.add(new PCBComponentXY(Component.getInt("x"),Component.getInt("y"),Component.getBoolean("Built-In"),Component.getInt("Id")));
                    }
                }
                instance.setPCBData(new PCBData(pcbData.getInt("length"), pcbData.getInt("width"),intWireArray,ComponentArray));
            }
            else{
                instance.setPCBData(new PCBData());
            }
        }
    }

}

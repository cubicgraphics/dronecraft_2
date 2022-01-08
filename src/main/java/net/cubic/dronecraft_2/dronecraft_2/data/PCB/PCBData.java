package net.cubic.dronecraft_2.dronecraft_2.data.PCB;


import net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB.IPCB;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import java.util.ArrayList;
import java.util.List;

public class PCBData {
    //this holds what is in the PCB
    public int length; //X
    public int width;  //Y
    public int[][] PCBWiresArray;
    public PCBComponentXY[] ComponentArray;

    public PCBData(int Length, int Width, int[][] Wires, List<PCBComponentXY> ComponentList){
        length = Length;
        width = Width;
        PCBWiresArray = Wires;
        ComponentArray = new PCBComponentXY[ComponentList.size()];
        ComponentList.toArray(ComponentArray);
    }

    public PCBData() {
        length = 16;
        width = 16;
        PCBWiresArray = new int[16][16];
        ComponentArray = new PCBComponentXY[0];
    }

    public INBT ToNBT() {
        CompoundNBT tag = new CompoundNBT();
        CompoundNBT PCBDATA = new CompoundNBT();
        ListNBT Wire2dArray = new ListNBT();
        PCBDATA.putInt("length", length);
        PCBDATA.putInt("width", width);
        //save the wires
        for (int i = 0; i < PCBWiresArray.length; i++) {
            CompoundNBT WireArray = new CompoundNBT();
            WireArray.putIntArray("Wire",PCBWiresArray[i]);
            Wire2dArray.add(WireArray);
        }
        PCBDATA.put("2dWireArray", Wire2dArray);
        //save the components
        ListNBT Components = new ListNBT();
        for (int i = 0; i < ComponentArray.length; i++) {
            CompoundNBT Component = new CompoundNBT();
            Component.putInt("ID",ComponentArray[i].ComponentID);
            Component.putInt("x",ComponentArray[i].x);
            Component.putInt("y",ComponentArray[i].y);
            Component.putBoolean("Built-In",ComponentArray[i].BuiltInComponent);
            Components.add(Component);
        }
        PCBDATA.put("Components", Components);
        tag.put("PCBData",PCBDATA);
        return tag;
    }

    public void FromNBT(INBT nbt) {
        System.out.println("Reading Dronecraft PCB data from Itemstack");
        CompoundNBT pcbData = (CompoundNBT) ((CompoundNBT) nbt).get("PCBData");
        if (pcbData != null) {
            //read wire array
            int[][] intWireArray = new int[pcbData.getInt("length")][pcbData.getInt("width")];
            List<PCBComponentXY> ComponentsList = new ArrayList<>();
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
                    ComponentsList.add(new PCBComponentXY(Component.getInt("x"),Component.getInt("y"),Component.getBoolean("Built-In"),Component.getInt("Id")));
                }
            }
            length = pcbData.getInt("length");
            width = pcbData.getInt("width");
            PCBWiresArray = intWireArray;
            ComponentArray = ComponentsList.toArray(ComponentArray);
        }
        else{
            length = 16;
            width = 16;
            PCBWiresArray = new int[16][16];
            ComponentArray = new PCBComponentXY[0];
        }
    }

    public static PCBData ReadNBT(INBT nbt) {
        PCBData data = new PCBData();

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
            data = new PCBData(pcbData.getInt("length"), pcbData.getInt("width"),intWireArray,ComponentArray);
        }
        return data;
    }
}

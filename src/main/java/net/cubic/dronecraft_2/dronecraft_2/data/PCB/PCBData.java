package net.cubic.dronecraft_2.dronecraft_2.data.PCB;


import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class PCBData {
    //this holds what is in the PCB
    public int length; //X
    public int width;  //Y
    public VarType[][] PCBWiresArray;
    public PCBComponentXY[] ComponentArray;
    public PCB_IO[] Inputs;
    public PCB_IO[] Outputs;
    //TODO add PCB_IO for inputs and outputs of the pcb

    public PCBData(int Length, int Width, VarType[][] Wires, List<PCBComponentXY> ComponentList){
        length = Length;
        width = Width;
        PCBWiresArray = Wires;
        ComponentArray = new PCBComponentXY[ComponentList.size()];
        ComponentList.toArray(ComponentArray);
        Inputs = null;
        Outputs = null;
    }

    public PCBData(int Length, int Width, VarType[][] Wires, List<PCBComponentXY> ComponentList,PCB_IO[] inputs,PCB_IO[] outputs){
        length = Length;
        width = Width;
        PCBWiresArray = Wires;
        ComponentArray = new PCBComponentXY[ComponentList.size()];
        ComponentList.toArray(ComponentArray);
        Inputs = inputs;
        Outputs = outputs;
    }

    public PCBData() {
        length = 16;
        width = 16;
        PCBWiresArray = new VarType[16][16];
        ComponentArray = new PCBComponentXY[0];
    }

    public INBT ToNBT() {
        CompoundNBT tag = new CompoundNBT();
        CompoundNBT PCBDATA = new CompoundNBT();
        PCBDATA.putInt("length", length);
        PCBDATA.putInt("width", width);
        //save the wires
        ListNBT Wire2dArray = new ListNBT();
        for (VarType[] varTypes : PCBWiresArray) {
            ListNBT Wire2dArray2 = new ListNBT();
            for (VarType varType : varTypes) {
                CompoundNBT N = new CompoundNBT();
                if(varType != null){
                    N.putString("D", varType.getRegistryName().toString());
                }
                else{
                    N.putString("D", "NULL");
                }
                Wire2dArray2.add(N);
            }
            Wire2dArray.add(Wire2dArray2);
        }
        PCBDATA.put("2dWireArray", Wire2dArray);
        //save the components
        ListNBT Components = new ListNBT();
        for (PCBComponentXY pcbComponentXY : ComponentArray) {
            CompoundNBT Component = new CompoundNBT();
            Component.putString("ID", pcbComponentXY.ComponentID.toString());
            Component.putInt("x", pcbComponentXY.x);
            Component.putInt("y", pcbComponentXY.y);
            Components.add(Component);
        }
        PCBDATA.put("Components", Components);
        //TODO add input and output saving
        tag.put("PCBData",PCBDATA);
        return tag;
    }

    public void FromNBT(INBT nbt) {
        System.out.println("Reading Dronecraft PCB data from Itemstack");
        CompoundNBT pcbData = (CompoundNBT) ((CompoundNBT) nbt).get("PCBData");
        if (pcbData != null) {
            //read wire array
            VarType[][] dataWireArray = new VarType[pcbData.getInt("length")][pcbData.getInt("width")];
            List<PCBComponentXY> ComponentsList = new ArrayList<>();
            ListNBT wireTagList = (ListNBT) (pcbData).get("2dWireArray");
            if(wireTagList != null){
                for (int i = 0; i < wireTagList.size(); i++) {
                    for (int j = 0; j < wireTagList.getList(i).size(); j++) {
                        if(!wireTagList.getList(i).getCompound(j).getString("D").equals("NULL")){
                            dataWireArray[i][j] = GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(wireTagList.getList(i).getCompound(j).getString("D")));
                        }
                    }
                }
            }
            //read component array
            ListNBT componentsTagList = (ListNBT) (pcbData).get("Components");
            if(componentsTagList != null){
                for (int i = 0; i < componentsTagList.size(); i++) {
                    CompoundNBT Component = componentsTagList.getCompound(i);
                    ComponentsList.add(new PCBComponentXY(Component.getInt("x"),Component.getInt("y"),new ResourceLocation(Component.getString("ID"))));
                }
            }
            length = pcbData.getInt("length");
            width = pcbData.getInt("width");
            //TODO add input and output loading

            PCBWiresArray = dataWireArray;
            ComponentArray = ComponentsList.toArray(ComponentArray);
        }
        else{
            length = 16;
            width = 16;
            PCBWiresArray = new VarType[16][16];
            ComponentArray = new PCBComponentXY[0];
        }
    }

    public static PCBData ReadNBT(INBT nbt) {
        PCBData data = new PCBData();

        CompoundNBT pcbData = (CompoundNBT) ((CompoundNBT) nbt).get("PCBData");
        if (pcbData != null) {
            //read wire array
            VarType[][] dataWireArray = new VarType[pcbData.getInt("length")][pcbData.getInt("width")];
            List<PCBComponentXY> ComponentArray = new ArrayList<>();
            ListNBT wireTagList = (ListNBT) (pcbData).get("2dWireArray");
            if(wireTagList != null){
                for (int i = 0; i < wireTagList.size(); i++) {
                    for (int j = 0; j < wireTagList.getList(i).size(); j++) {
                        if(!wireTagList.getList(i).getCompound(j).getString("D").equals("NULL")) {
                            dataWireArray[i][j] = GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(wireTagList.getList(i).getCompound(j).getString("D")));
                        }
                    }
                }
            }
            //read component array
            ListNBT componentsTagList = (ListNBT) (pcbData).get("Components");
            if(componentsTagList != null){
                for (int i = 0; i < componentsTagList.size(); i++) {
                    CompoundNBT Component = componentsTagList.getCompound(i);
                    ComponentArray.add(new PCBComponentXY(Component.getInt("x"),Component.getInt("y"),new ResourceLocation(Component.getString("ID"))));
                }
            }
            //TODO add input and output loading

            data = new PCBData(pcbData.getInt("length"), pcbData.getInt("width"),dataWireArray,ComponentArray);
        }
        return data;
    }
}

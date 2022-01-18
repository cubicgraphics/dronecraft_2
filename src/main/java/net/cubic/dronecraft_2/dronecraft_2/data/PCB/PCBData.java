package net.cubic.dronecraft_2.dronecraft_2.data.PCB;


import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.DefaultPCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class PCBData {
    //this holds what is in the PCB
    public int length; //X
    public int width;  //Y
    public VarType[][] PCBWiresArray;
    public PCBComponentXY<? extends DefaultPCBComponent>[] ComponentArray;
    public PCB_IO[] Inputs;
    public PCB_IO[] Outputs;

    public PCBData(int Length, int Width, VarType[][] Wires, List<PCBComponentXY<? extends DefaultPCBComponent>> ComponentList){
        length = Length;
        width = Width;
        PCBWiresArray = Wires;
        ComponentArray = new PCBComponentXY<?>[ComponentList.size()];
        ComponentList.toArray(ComponentArray);
        Inputs = null;
        Outputs = null;
    }

    public PCBData(int Length, int Width, VarType[][] Wires, List<PCBComponentXY<? extends DefaultPCBComponent>> ComponentList, PCB_IO[] inputs, PCB_IO[] outputs){
        length = Length;
        width = Width;
        PCBWiresArray = Wires;
        ComponentArray = new PCBComponentXY<?>[ComponentList.size()];
        ComponentList.toArray(ComponentArray);
        Inputs = inputs;
        Outputs = outputs;
    }

    public PCBData() {
        length = 16;
        width = 16;
        PCBWiresArray = new VarType[16][16];
        ComponentArray = new PCBComponentXY<?>[0];
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
        for (PCBComponentXY<? extends DefaultPCBComponent> pcbComponentXY : ComponentArray) {
            CompoundNBT Component = new CompoundNBT();
            Component.putString("ID", pcbComponentXY.Component.getRegistryName().toString());
            Component.putInt("x", pcbComponentXY.x);
            Component.putInt("y", pcbComponentXY.y);
            pcbComponentXY.Component.SaveCustomVarToNBT();
            if(pcbComponentXY.Component.ReadNBT() != null){
                Component.put("component_nbt",pcbComponentXY.Component.ReadNBT());
            }
            Components.add(Component);
        }
        PCBDATA.put("Components", Components);



        ListNBT InputsList = new ListNBT();
        if(Inputs != null){
            for (PCB_IO input : Inputs) {
                CompoundNBT N = new CompoundNBT();
                N.putString("R", input.DataType.getRegistryName().toString());
                N.putInt("X", input.X);
                N.putInt("Y", input.Y);
                InputsList.add(N);
            }
        }
        tag.put("Inputs",InputsList);
        ListNBT OutputsList = new ListNBT();
        if(Outputs != null){
            for (PCB_IO input : Outputs) {
                CompoundNBT N = new CompoundNBT();
                N.putString("R", input.DataType.getRegistryName().toString());
                N.putInt("X", input.X);
                N.putInt("Y", input.Y);
                InputsList.add(N);
            }
        }
        tag.put("Outputs",OutputsList);
        tag.put("PCBData",PCBDATA);
        return tag;
    }

    public void FromNBT(INBT nbt) {
        System.out.println("Reading Dronecraft PCB data from Itemstack");
        CompoundNBT pcbData = (CompoundNBT) ((CompoundNBT) nbt).get("PCBData");
        if (pcbData != null) {
            //read wire array
            VarType[][] dataWireArray = new VarType[pcbData.getInt("length")][pcbData.getInt("width")];
            List<PCBComponentXY<? extends DefaultPCBComponent>> ComponentsList = new ArrayList<>();
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
                    PCBComponentXY<? extends DefaultPCBComponent> temp = new PCBComponentXY<>(Component.getInt("x"),Component.getInt("y"),GameRegistry.findRegistry(DefaultPCBComponent.class).getValue(new ResourceLocation(Component.getString("ID"))));
                    temp.Component.SetNBT((CompoundNBT) Component.get("component_nbt"));
                    temp.Component.SaveCustomVarToNBT();
                    ComponentsList.add(temp);
                }
            }
            length = pcbData.getInt("length");
            width = pcbData.getInt("width");
            ListNBT nbtInputsList = (ListNBT) (pcbData).get("Inputs");
            if(nbtInputsList != null){
                PCB_IO[] inputs = new PCB_IO[nbtInputsList.size()];
                for (int i = 0; i < nbtInputsList.size(); i++) {
                    inputs[i]= new PCB_IO(nbtInputsList.getCompound(i).getInt("X"),nbtInputsList.getCompound(i).getInt("Y"),GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(nbtInputsList.getCompound(i).getString("R"))));

                }
                Inputs = inputs;
            }
            else{
                Inputs = null;
            }
            ListNBT nbtOutputsList = (ListNBT) (pcbData).get("Outputs");
            if(nbtOutputsList != null){
                PCB_IO[] outputs = new PCB_IO[nbtOutputsList.size()];
                for (int i = 0; i < nbtOutputsList.size(); i++) {
                    outputs[i]= new PCB_IO(nbtOutputsList.getCompound(i).getInt("X"),nbtOutputsList.getCompound(i).getInt("Y"),GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(nbtOutputsList.getCompound(i).getString("R"))));

                }
                Outputs = outputs;
            }
            else{
                Outputs = null;
            }


            PCBWiresArray = dataWireArray;
            ComponentArray = ComponentsList.toArray(ComponentArray);
        }
        else{
            length = 16;
            width = 16;
            PCBWiresArray = new VarType[16][16];
            ComponentArray = new PCBComponentXY<?>[0];
            Inputs = new PCB_IO[0];
        }
    }

    public static PCBData ReadNBT(INBT nbt) {
        PCBData data = new PCBData();

        CompoundNBT pcbData = (CompoundNBT) ((CompoundNBT) nbt).get("PCBData");
        if (pcbData != null) {
            //read wire array
            VarType[][] dataWireArray = new VarType[pcbData.getInt("length")][pcbData.getInt("width")];
            List<PCBComponentXY<? extends DefaultPCBComponent>> ComponentArray = new ArrayList<>();
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
                    PCBComponentXY<? extends DefaultPCBComponent> temp = new PCBComponentXY<>(Component.getInt("x"),Component.getInt("y"),GameRegistry.findRegistry(DefaultPCBComponent.class).getValue(new ResourceLocation(Component.getString("ID"))));
                    temp.Component.SetNBT((CompoundNBT) Component.get("component_nbt"));
                    temp.Component.SaveCustomVarToNBT();
                    ComponentArray.add(temp);
                }
            }
            ListNBT nbtInputsList = (ListNBT) (pcbData).get("Inputs");
            PCB_IO[] inputs;
            if(nbtInputsList != null){
                inputs = new PCB_IO[nbtInputsList.size()];
                for (int i = 0; i < nbtInputsList.size(); i++) {
                    inputs[i]= new PCB_IO(nbtInputsList.getCompound(i).getInt("X"),nbtInputsList.getCompound(i).getInt("Y"),GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(nbtInputsList.getCompound(i).getString("R"))));

                }
            }
            else{
                inputs = null;
            }
            ListNBT nbtOutputsList = (ListNBT) (pcbData).get("Outputs");
            PCB_IO[] outputs;
            if(nbtOutputsList != null){
                outputs = new PCB_IO[nbtOutputsList.size()];
                for (int i = 0; i < nbtOutputsList.size(); i++) {
                    outputs[i]= new PCB_IO(nbtOutputsList.getCompound(i).getInt("X"),nbtOutputsList.getCompound(i).getInt("Y"),GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(nbtOutputsList.getCompound(i).getString("R"))));

                }
            }
            else{
                outputs = null;
            }



            data = new PCBData(pcbData.getInt("length"), pcbData.getInt("width"),dataWireArray,ComponentArray,inputs,outputs);
        }
        return data;
    }
}

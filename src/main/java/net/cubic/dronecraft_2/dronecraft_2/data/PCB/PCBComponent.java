package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraft.util.text.Color;

public class PCBComponent {
    //A component is a part of the PCB - they get dragged into place in the builder gui
    public String ComponentName;
    public int Length; //x  Size it will take on the PCB designer
    public int Width; //y
    public PCB_IO[] Inputs;
    public PCB_IO[] Outputs;
    public RGBA ComponentColor;
    public PCBData PCBData; //set to null if it is a built-in pcb
    public String Instruction;
    public PCBSymbol[] Decals;


    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = null;
        Instruction = null;
        Decals = null;
    }
    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBData PcbData){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = PcbData;
        Instruction = null;
        Decals = null;
    }
    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, String instruction){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = instruction;
        PCBData = null;
        Decals = null;
    }

    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol decal){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = null;
        Instruction = null;
        Decals = new PCBSymbol[]{
                decal
        };
    }
    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBData PcbData, PCBSymbol decal){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = PcbData;
        Instruction = null;
        Decals = new PCBSymbol[]{
                decal
        };
    }
    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, String instruction, PCBSymbol decal){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = instruction;
        PCBData = null;
        Decals = new PCBSymbol[]{
                decal
        };
    }
    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol[] decals){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = null;
        Instruction = null;
        Decals = decals;
    }
    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBData PcbData, PCBSymbol[] decals){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = PcbData;
        Instruction = null;
        Decals = decals;
    }
    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, String instruction, PCBSymbol[] decals){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = instruction;
        PCBData = null;
        Decals = decals;
    }
}

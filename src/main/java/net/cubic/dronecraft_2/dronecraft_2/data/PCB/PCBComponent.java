package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraft.util.text.Color;

public class PCBComponent {
    //A component is a part of the PCB - they get dragged into place in the builder gui
    public String ComponentName;
    public int Width; //Size it will take on the PCB designer
    public int Length;
    public PCB_IO[] Inputs;
    public PCB_IO[] Outputs;
    public RGBA ComponentColor;
    public PCBData PCBData; //set to null if it is a built-in pcb

    public PCBComponent(String Name, int width, int length, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBData PcbData){
        ComponentName = Name;
        Width = width;
        Length = length;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = PcbData;
    }
}

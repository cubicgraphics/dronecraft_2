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

    public PCBComponent(String Name, int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBData PcbData){
        ComponentName = Name;
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        PCBData = PcbData;
    }
}

package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.minecraft.util.text.Color;

public class PCBComponent {
    //A component is a part of the PCB - they get dragged into place in the builder gui
    public String ComponentName;
    public int Width; //Size it will take on the PCB designer
    public int Length;
    public PCB_IO[] Inputs;
    public PCB_IO[] Outputs;
    public Color ComponentColor;

    public PCBComponent(String Name, int width, int length, PCB_IO[] inputs, PCB_IO[] outputs, Color color){
        ComponentName = Name;
        Width = width;
        Length = length;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
    }
}

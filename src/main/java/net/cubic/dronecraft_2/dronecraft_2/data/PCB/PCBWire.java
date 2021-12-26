package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.minecraft.util.text.Color;

public class PCBWire {
    public String Name;
    public Color Color;
    public float Multiplier;

    public PCBWire(String name, Color color,float multiplier){
        Name = name;
        Color = color;
        Multiplier = multiplier;
    }
}

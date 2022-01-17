package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class PCBComponentXY<T extends PCBComponent> {
    public int x;
    public int y;
    public T Component;


    public PCBComponentXY(int X, int Y, T pcbComponent){
        x = X;
        y = Y;
        Component = pcbComponent;
    }
}

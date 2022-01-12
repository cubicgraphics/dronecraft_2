package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PCBComponentXY {
    public int x;
    public int y;
    public net.minecraft.util.ResourceLocation ComponentID;


    public PCBComponentXY(int X, int Y, PCBComponent pcbComponent){
        x = X;
        y = Y;
        ComponentID = GameRegistry.findRegistry(PCBComponent.class).getKey(pcbComponent);
    }
    public PCBComponentXY(int X, int Y, net.minecraft.util.ResourceLocation ComponentId){
        x = X;
        y = Y;
        ComponentID = ComponentId;
    }

    public PCBComponent getComponent(){
        return GameRegistry.findRegistry(PCBComponent.class).getValue(ComponentID);
    }
}

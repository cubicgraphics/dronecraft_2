package net.cubic.dronecraft_2.dronecraft_2.data.PCB;


import java.util.List;

public class PCBData {
    //this holds what is in the PCB

    public int[][] PCBWiresArray;
    public ComponentXY[] ComponentArray;

    public PCBData(int[][] Wires, List<ComponentXY> ComponentList){
        PCBWiresArray = Wires;
        ComponentArray = new ComponentXY[ComponentList.size()];
        ComponentList.toArray(ComponentArray);
    }
}

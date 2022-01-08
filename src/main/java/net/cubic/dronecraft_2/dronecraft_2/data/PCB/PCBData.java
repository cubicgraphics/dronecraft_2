package net.cubic.dronecraft_2.dronecraft_2.data.PCB;


import java.util.List;

public class PCBData {
    //this holds what is in the PCB
    public int length; //X
    public int width;  //Y
    public int[][] PCBWiresArray;
    public PCBComponentXY[] ComponentArray;

    public PCBData(int Length, int Width, int[][] Wires, List<PCBComponentXY> ComponentList){
        length = Length;
        width = Width;
        PCBWiresArray = Wires;
        ComponentArray = new PCBComponentXY[ComponentList.size()];
        ComponentList.toArray(ComponentArray);
    }

    public PCBData() {
        length = 16;
        width = 16;
        PCBWiresArray = new int[16][16];
        ComponentArray = new PCBComponentXY[0];
    }
}

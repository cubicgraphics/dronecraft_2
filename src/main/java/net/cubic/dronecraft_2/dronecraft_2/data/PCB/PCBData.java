package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import java.util.List;

public class PCBData {
    //this holds the PCB Array - the part that stores what is in the PCB

    public int[][] PCBComponentArray;
    public int[][] PCBWiresArray;
    
    public PCBData(int Width, int Length){
        PCBComponentArray = new int[Width][Length];
        PCBWiresArray = new int[Width][Length];
    }
}

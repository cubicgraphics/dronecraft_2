package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

public class PCBData {
    //this holds the PCB Array - the part that stores what is in the PCB

    public int[][] PCBArray;

    public PCBData(int Width, int Length){
        PCBArray = new int[Width][Length];
    }

    //when drawing the pcb, just draw null indexes as the pcb background color
}

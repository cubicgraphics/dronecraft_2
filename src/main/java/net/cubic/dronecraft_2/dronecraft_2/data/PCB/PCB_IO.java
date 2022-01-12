package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;

public class PCB_IO {
    public int X;
    public int Y;
    public VarType DataType;

    public PCB_IO(int x, int y, VarType dataType){
        X = x;
        Y = y;
        DataType = dataType;
    }
}

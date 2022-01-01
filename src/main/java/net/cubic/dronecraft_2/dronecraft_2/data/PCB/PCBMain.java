package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;

import java.util.Arrays;
import java.util.List;

public class PCBMain {

    public static PCB_IO[] simpleIn = {
            new PCB_IO(0,0,"number"),
            new PCB_IO(2,0,"number")
    };
    public static PCB_IO[] simpleOut = {
            new PCB_IO(1,1,"number")
    };

    public static PCB_IO[] simpleDroneIn = {
            new PCB_IO(0,2,"DronePositionData"),
            new PCB_IO(0,5,"DroneGoalData")
    };
    public static PCB_IO[] simpleDroneOut = {
            new PCB_IO(7,2,"DroneActionData"),
    };

    public static PCBComponent[] Components= new PCBComponent[]{
            new PCBComponent("Add", 3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33), "add",new PCBSymbol(1,0.5F,0,16)),
            new PCBComponent("Sub", 3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33), "sub",new PCBSymbol(1,0.5F,8,16)),
            new PCBComponent("Multiply", 3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33), "multiply",new PCBSymbol(1,0.5F,24,16)),
            new PCBComponent("Divide", 3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33), "divide",new PCBSymbol(1,0.5F,16,16)),
            new PCBComponent("Equals", 3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33), "equals",new PCBSymbol(1,0.5F,32,16)),

            new PCBComponent("SimpleDroneBrain", 8, 8, simpleDroneIn, simpleDroneOut, new RGBA(43, 150, 0),new PCBSymbol(3,3,16,32,16,16)),
    };

    public static PCBComponentXY[] componentXY = new PCBComponentXY[]{
            new PCBComponentXY(3,4,true,0)
    };

    public static int[][] intarray = new int[8][8];
    static {
        intarray[0][0] = 1;
        intarray[0][1] = 1;
        intarray[0][2] = 1;
    }

    public static PCBData[] PCBdataTest = new PCBData[]{
            new PCBData(8,8,intarray,Arrays.asList(componentXY))
    };

    public List<PCBComponent> PlayerMadeComponents; //player made components
}
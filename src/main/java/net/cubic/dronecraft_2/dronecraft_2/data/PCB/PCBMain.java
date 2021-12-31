package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;

import java.util.HashMap;
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
            new PCBComponent("Add", 3, 2, simpleIn, simpleOut, new RGBA(),null),
            new PCBComponent("Sub", 3, 2, simpleIn, simpleOut, new RGBA(),null),
            new PCBComponent("Multiply", 3, 2, simpleIn, simpleOut, new RGBA(),null),
            new PCBComponent("Divide", 3, 2, simpleIn, simpleOut, new RGBA(),null),
            new PCBComponent("Equals", 3, 2, simpleIn, simpleOut, new RGBA(),null),


            new PCBComponent("SimpleDroneBrain", 8, 8, simpleDroneIn, simpleDroneOut, new RGBA(43, 150, 0),null),
    };

    public List<PCBComponent> PlayerMadeComponents; //player made components

    public static HashMap<Integer,String> ComponentToInstruction = new HashMap<Integer,String>(){{
        put(0, "add");
        put(1, "sub");
        put(2, "multiply");
        put(3, "divide");
        put(4, "equals");
    }}; //links all built in components by int ID to there respectful instruction, if they have one
}
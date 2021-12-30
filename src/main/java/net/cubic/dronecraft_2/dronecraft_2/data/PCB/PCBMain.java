package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraft.util.text.Color;

import java.util.HashMap;
import java.util.List;

public class PCBMain {
    PCBComponent[] Components; //built in components
    List<PCBComponent> PlayerMadeComponents; //player made components
    HashMap<Integer,String> ComponentToInstruction; //links all built in components by int ID to there respectful instruction, if they have one

    public PCBMain(){
        //TODO learn how to load from resources so the defaults can be loaded from there instead of here

        PCB_IO[] simpleIn = {
                new PCB_IO(0,0,"number"),
                new PCB_IO(2,0,"number")
        };
        PCB_IO[] simpleOut = {
                new PCB_IO(1,0,"number")
        };

        PCB_IO[] simpleDroneIn = {
                new PCB_IO(0,2,"DronePositionData"),
                new PCB_IO(0,5,"DroneGoalData")
        };
        PCB_IO[] simpleDroneOut = {
                new PCB_IO(7,2,"DroneActionData"),
        };

        Components = new PCBComponent[]{
            new PCBComponent("Add", 2, 1, simpleIn, simpleOut, new RGBA(),null),
            new PCBComponent("Sub", 2, 1, simpleIn, simpleOut, new RGBA(),null),
            new PCBComponent("Multiply", 2, 1, simpleIn, simpleOut, new RGBA(),null),
            new PCBComponent("Divide", 2, 1, simpleIn, simpleOut, new RGBA(),null),

            new PCBComponent("SimpleDroneBrain", 8, 8, simpleDroneIn, simpleDroneOut, new RGBA(),null),
        };
    }
}

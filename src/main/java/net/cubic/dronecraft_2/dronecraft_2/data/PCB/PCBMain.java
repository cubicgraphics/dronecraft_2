package net.cubic.dronecraft_2.dronecraft_2.data.PCB;



import com.google.gson.Gson;
import net.minecraft.util.text.Color;

import java.util.HashMap;
import java.util.List;

public class PCBMain {
    PCBComponent[] Components; //built in components
    List<PCBComponent> PlayerMadeComponents; //player made components
    PCBWire[] Wires; //built in wire types
    HashMap<Integer,String> ComponentToInstruction; //links all built in components by int ID to there respectful instruction
    HashMap<PCBComponent,PCBData> PCBToPCBLayout; //links all PCBComponents to a PCB layout

    public PCBMain(){
        //TODO learn how to load from resources so the defaults can be loaded from there instead of here
        PCB_IO[] simpleIn = {
                new PCB_IO(0,0,"number"),
                new PCB_IO(2,0,"number")
        };
        PCB_IO[] simpleOut = {
                new PCB_IO(1,0,"number")
        };
        Components = new PCBComponent[]{
            new PCBComponent("Add", 2, 1, simpleIn, simpleOut, Color.fromHex("#2a6e3c")),
            new PCBComponent("Sub", 2, 1, simpleIn, simpleOut, Color.fromHex("#2a6e3c")),
            new PCBComponent("Multiply", 2, 1, simpleIn, simpleOut, Color.fromHex("#2a6e3c")),
            new PCBComponent("Divide", 2, 1, simpleIn, simpleOut, Color.fromHex("#2a6e3c")),

            new PCBComponent("SimpleDroneBrain", 8, 8, simpleIn, simpleOut, Color.fromHex("#2b4532")),

        };
    }
}

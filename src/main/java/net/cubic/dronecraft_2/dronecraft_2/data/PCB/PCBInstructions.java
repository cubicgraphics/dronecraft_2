package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions.*;

import java.util.HashMap;

public class PCBInstructions {

    public static HashMap<String, Instruction<?>> InstructionMap = new HashMap<>();
    //To add a new pcb instruction just put a new one into this hashmap

    static {
        InstructionMap.put("add", new Instruction<>(new addInstruction()));
        InstructionMap.put("sub", new Instruction<>(new subInstruction()));
        InstructionMap.put("multiply", new Instruction<>(new multiplyInstruction()));
        InstructionMap.put("divide", new Instruction<>(new divideInstruction()));
        InstructionMap.put("equals", new Instruction<>(new EqualsInstruction()));

    }
    //TODO need to add a check that is run to make sure that the data is all of equal type - should be run in the thing that creates the pcb before any instructions are called anyway

    //PCBInstructions.InstructionMap.get("add").instruction.run(Data);
}
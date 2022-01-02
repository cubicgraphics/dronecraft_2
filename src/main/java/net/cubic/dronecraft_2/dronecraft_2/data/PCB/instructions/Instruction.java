package net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions;

public class Instruction<T extends GenericInstruction> {
    public T instruction;
    public Instruction(T INSTRUCTION){
        this.instruction = INSTRUCTION;
    }

}

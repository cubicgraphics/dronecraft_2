package net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class Instruction<T extends GenericInstruction<T>> {
    public T instruction;
    public Instruction(T INSTRUCTION){
        this.instruction = INSTRUCTION;
    }

}

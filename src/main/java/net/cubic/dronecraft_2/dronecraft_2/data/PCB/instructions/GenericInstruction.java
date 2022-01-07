package net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_ID;

public interface GenericInstruction<T> {

    PCB_ID<?> run(PCB_ID<T>[] data);

}

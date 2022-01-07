package net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_ID;


public class EqualsInstruction<T> implements GenericInstruction<T>{

    @Override
    public PCB_ID<?> run(PCB_ID<T>[] data) {
        boolean AllEqual = true;
        for (int i = 1; i < data.length; i++) {
            if (data[i].data != data[i - 1].data) {
                AllEqual = false;
                break;
            }
        }
        return new PCB_ID<>(AllEqual);
    }
}

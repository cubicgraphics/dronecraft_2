package net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_ID;

public class multiplyInstruction implements GenericInstruction{

    @Override
    public PCB_ID<?> run(PCB_ID<?>[] data) {
        if(data[0].data instanceof Double){
            double v = 1D * (double)data[0].data * (double) data[1].data;
            for (int i = 2; i < data.length; i++) {
                v = v * (double)data[i].data;
            }
            return new PCB_ID<>(v);
        }
        else{
            return new PCB_ID<>();
        }
    }
}
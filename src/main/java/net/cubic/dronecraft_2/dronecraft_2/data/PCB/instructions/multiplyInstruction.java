package net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_ID;


public class multiplyInstruction<T> implements GenericInstruction<T>{

    @Override
    public PCB_ID<?> run(PCB_ID<T>[] data) {
        if(data[0].data instanceof Double && data[1].data instanceof Double){
            double v = 1D * (Double)data[0].data * (Double) data[1].data;
            for (int i = 2; i < data.length; i++) {
                if(data[i].data instanceof Number) {
                    v = v * (Double) data[i].data;
                }
            }
            return new PCB_ID<>(v);
        }
        else{
            return new PCB_ID<>();
        }
    }
}
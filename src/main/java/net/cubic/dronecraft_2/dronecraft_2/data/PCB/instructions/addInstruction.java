package net.cubic.dronecraft_2.dronecraft_2.data.PCB.instructions;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_ID;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.io.Serializable;


public class addInstruction<T> implements GenericInstruction<T>{
    ResourceLocation ID;

    @Override
    public PCB_ID<?> run(PCB_ID<T>[] data) {
        if(data[0].data instanceof Number && data[1].data instanceof Number){
            double v = (Double)data[0].data + (Double) data[1].data;
            for (int i = 2; i < data.length; i++) {
                if(data[i].data instanceof Number){
                    v = v + (Double)data[i].data;
                }
            }
            return new PCB_ID<>(v);
        }
        else if(data[0].data instanceof String && data[1].data instanceof String){
            String v = (String)data[0].data + (String)data[1].data;
            for (int i = 2; i < data.length; i++) {
                if(data[i].data instanceof String){
                    v = v + (String)data[i].data;
                }
            }
            return new PCB_ID<>(v);
        }
        else{
            return new PCB_ID<>();
        }
    }
}
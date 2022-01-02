package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

public class PCB_ID<T>{
    //PCB Instruction Data
    public T data;

    public PCB_ID(){
        data = null;
    }
    public PCB_ID(T Data){
        data = Data;
    }

}

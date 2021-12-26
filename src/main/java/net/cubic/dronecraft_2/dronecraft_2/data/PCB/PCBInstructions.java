package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import java.util.Objects;

public class PCBInstructions {
    public static Object Process(String Instruction, Object[] Data){ //This contains the instruction set for the built-in PCB components
        Object returnValue = new Object();

        if (Objects.equals(Instruction, "add")){
            if(AllOfDataType(Data,double.class)){
                returnValue = 0;
                for (Object datum : Data) {
                    returnValue = (double)returnValue + (double) datum;
                }
            }
        }

        else if (Objects.equals(Instruction, "sub")){
            if(AllOfDataType(Data,double.class)){
                returnValue = 0;
                for (Object datum : Data) {
                    returnValue = (double)returnValue - (double) datum;
                }
            }
        }

        else if (Objects.equals(Instruction, "multiply")){
            if(AllOfDataType(Data,double.class)){
                    returnValue = 1;
                    for (Object datum : Data) {
                        returnValue = (double) returnValue * (double) datum;
                    }
                }
        }

        else if (Objects.equals(Instruction, "divide")) {
            if(AllOfDataType(Data,double.class)) {
                return (double) Data[0] / (double) Data[1];
            }
        }

        else if (Objects.equals(Instruction, "Equ")) {
            returnValue = true;
            for (int i = 1; i < Data.length; i++) {
                if (Data[i] != Data[i - 1]) {
                    returnValue = false;
                    break;
                }
            }
        }
        return returnValue;
    }



    public static boolean AllOfDataType(Object[] Data, Class<?> type){
        boolean returnValue = true;
        for (Object datum : Data) {
            if (datum.getClass() != type) {
                returnValue = false;
                break;
            }
        }
        return returnValue;
    }
}

package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;

import java.util.Arrays;

public class PCBtest {

    //this is a test pcb for testing purposes
    public static PCBComponentXY<?>[] componentXY = new PCBComponentXY[]{
            new PCBComponentXY<>(3,4, PCBComponents.SMALL_ADD.delegate.get()),
            new PCBComponentXY<>(5,4, PCBComponents.STATIC_BOOL.delegate.get())
    };

    public static VarType[][] wireArray = new VarType[8][8];
    static {
        wireArray[0][0] = PCBVarTypes.XYZ;
        wireArray[0][1] = PCBVarTypes.BOOLEAN;
        wireArray[0][2] = PCBVarTypes.NUMBER;
    }

    public static PCBData[] PCBdataTest = new PCBData[]{
            new PCBData(8,8,wireArray,Arrays.asList(componentXY))
    };
}
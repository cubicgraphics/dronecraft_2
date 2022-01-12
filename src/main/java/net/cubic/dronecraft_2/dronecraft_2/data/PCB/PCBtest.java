package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PCBtest {

    //this is a test pcb for testing purposes
    public static PCBComponentXY[] componentXY = new PCBComponentXY[]{
            new PCBComponentXY(3,4, PCBComponents.SMALL_ADD)
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
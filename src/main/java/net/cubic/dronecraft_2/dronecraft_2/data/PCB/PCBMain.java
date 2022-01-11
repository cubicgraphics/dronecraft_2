package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PCBMain {

    //this is a test pcb for testing purposes
    public static PCBComponentXY[] componentXY = new PCBComponentXY[]{
            new PCBComponentXY(3,4, PCBComponents.SMALL_ADD)
    };

    public static int[][] intarray = new int[8][8];
    static {
        intarray[0][0] = 1;
        intarray[0][1] = 1;
        intarray[0][2] = 1;
    }

    public static PCBData[] PCBdataTest = new PCBData[]{
            new PCBData(8,8,intarray,Arrays.asList(componentXY))
    };
}
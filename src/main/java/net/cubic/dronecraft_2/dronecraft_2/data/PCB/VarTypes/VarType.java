package net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;

public class VarType extends net.minecraftforge.registries.ForgeRegistryEntry<VarType> implements IVarType{
    private final Class<?> DataType;
    private final RGBA Color;

    public VarType(RGBA color, Class<?> dataType){
        DataType = dataType;
        Color = color;
    }

    @Override
    public RGBA getColor() {
        return Color;
    }

    @Override
    public Class<?> getDataType() {
        return DataType;
    }

}

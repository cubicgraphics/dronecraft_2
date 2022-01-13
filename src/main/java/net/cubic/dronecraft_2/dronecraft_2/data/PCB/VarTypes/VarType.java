package net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraft.item.crafting.Ingredient;

public class VarType extends net.minecraftforge.registries.ForgeRegistryEntry<VarType> implements IVarType{
    private final Class<?> DataType;
    private final RGBA Color;
    private final Ingredient Ingredients;

    public VarType(RGBA color, Class<?> dataType, Ingredient ingredients){
        DataType = dataType;
        Color = color;
        Ingredients = ingredients;
    }

    @Override
    public RGBA getColor() {
        return Color;
    }

    @Override
    public Class<?> getDataType() {
        return DataType;
    }

    @Override
    public Ingredient getWireIngredients() {
        return Ingredients;
    }


}

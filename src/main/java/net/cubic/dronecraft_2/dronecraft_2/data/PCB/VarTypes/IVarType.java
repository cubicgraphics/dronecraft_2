package net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraft.item.crafting.Ingredient;

public interface IVarType {

    RGBA getColor();

    Class<?> getDataType();

}

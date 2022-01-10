package net.cubic.dronecraft_2.dronecraft_2.item.circuits;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PCBSubstrate extends Item {
    public PCBSubstrate(Properties properties) {
        super(properties);
    }

    public boolean hasColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("colorR", 99)&& compoundnbt.contains("colorG", 99) && compoundnbt.contains("colorB", 99);
    }

    public RGBA getColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        if(compoundnbt != null && hasColor(stack)){
            return new RGBA(compoundnbt.getFloat("colorR"),compoundnbt.getFloat("colorG"),compoundnbt.getFloat("colorB"));
        }
        else{
            return new RGBA();
        }
    }

    public void removeColor(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        if (compoundnbt != null && hasColor(stack)) {
            compoundnbt.remove("colorR");
            compoundnbt.remove("colorG");
            compoundnbt.remove("colorB");
        }

    }

    public void setColor(ItemStack stack, RGBA color) {
        stack.getOrCreateChildTag("display").putFloat("colorR", color.r);
        stack.getOrCreateChildTag("display").putFloat("colorG", color.g);
        stack.getOrCreateChildTag("display").putFloat("colorB", color.b);
    }
    //The pcb before the Layered pcb item - should work
}

package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.item.circuits.PCBSubstrate;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class PCBSubstrateColor implements IItemColor
{
    @Override
    public int getColor(@Nonnull ItemStack stack, int tintIndex)
    {
        if(((PCBSubstrate)stack.getItem()).hasColor(stack)){
            RGBA color = new RGBA(((PCBSubstrate)stack.getItem()).getColor(stack).r,((PCBSubstrate)stack.getItem()).getColor(stack).g,((PCBSubstrate)stack.getItem()).getColor(stack).b);
            return color.ToRawInt();
        }
        else{
            return (0xFFFFFFFF);
        }
    }
}

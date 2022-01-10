package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import javax.annotation.Nonnull;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.item.circuits.PCBSubstrate;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class PCBSubstrateColor implements IItemColor
{
    @Override
    public int getColor(@Nonnull ItemStack stack, int tintIndex)
    {
        if(((PCBSubstrate)stack.getItem()).hasColor(stack)){
            System.out.println("getting the color");
            RGBA color = new RGBA(((PCBSubstrate)stack.getItem()).getColor(stack).r,((PCBSubstrate)stack.getItem()).getColor(stack).g,((PCBSubstrate)stack.getItem()).getColor(stack).b);
            return color.ToRawInt();
        }
        else{
            return (0xFFFFFFFF);
        }
    }
}

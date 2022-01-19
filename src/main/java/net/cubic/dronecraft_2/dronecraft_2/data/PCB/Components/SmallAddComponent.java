package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBSymbol;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_IO;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class SmallAddComponent extends DefaultPCBComponent implements IPCBComponent{
    public SmallAddComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol pcbSymbol, TYPE type) {
        super(length, width, inputs, outputs, color,pcbSymbol, type);
    }

    @Override
    public List<?> CalculateOutput(List<?> inputs, World worldIn, BlockPos pos, Entity entity, ItemStack item, PCBComponentXY<?> Component) {
        if(inputs.get(0) instanceof String){
            List<String> Out = new ArrayList<>();
            Out.add((String)inputs.get(0) + (String)inputs.get(1));
            return Out;
        }
        else{
            List<Double> Out = new ArrayList<>();
            Out.add((Double)inputs.get(0) + (Double)inputs.get(1));
            return Out;
        }
    }

    @Override
    public boolean CheckDataEquals(List<?> inputs) {
        if(inputs.size() == Inputs.length){
            return (inputs.get(0).getClass() == String.class && inputs.get(1).getClass() == String.class) || (inputs.get(0).getClass() == Double.class && inputs.get(1).getClass() == Double.class);
        }
        else{
            return false;
        }
    }
}

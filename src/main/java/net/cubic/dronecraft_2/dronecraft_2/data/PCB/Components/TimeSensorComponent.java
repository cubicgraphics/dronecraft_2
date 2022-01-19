package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBSymbol;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_IO;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TimeSensorComponent extends DefaultPCBComponent implements IPCBComponent{
    public TimeSensorComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol pcbSymbol, TYPE type) {
        super(length, width, inputs, outputs, color,pcbSymbol, type);
    }

    @Override
    public List<?> CalculateOutput(List<?> inputs, World worldIn, BlockPos pos, Entity entity, ItemStack item, PCBComponentXY<?> Component) {
        List<Double> Out = new ArrayList<>();
        Out.add((double) worldIn.getDayTime());
        return Out;
    }
}

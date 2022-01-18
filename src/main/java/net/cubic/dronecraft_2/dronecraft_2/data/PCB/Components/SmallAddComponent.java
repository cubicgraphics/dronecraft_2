package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBSymbol;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_IO;

import java.util.ArrayList;
import java.util.List;

public class SmallAddComponent extends DefaultPCBComponent implements IPCBComponent{
    public SmallAddComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol pcbSymbol, TYPE type) {
        super(length, width, inputs, outputs, color,pcbSymbol, type);
    }

    @Override
    public List<?> CalculateOutput(List<?> inputs) {
        List<Double> Out = new ArrayList<>();
        Out.add((Double)inputs.get(0) + (Double)inputs.get(1));
        return Out;
    }
}

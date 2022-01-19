package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBSymbol;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_IO;
import net.cubic.dronecraft_2.dronecraft_2.screen.PCBRender;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class StaticBoolComponent extends DefaultPCBComponent implements IPCBComponent{

    public StaticBoolComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, TYPE type) {
        super(length, width, inputs, outputs, color, type);
    }

    @Override
    public List<?> CalculateOutput(List<?> inputs, World worldIn, BlockPos pos, Entity entity, ItemStack item, PCBComponentXY<?> Component) {
        List<Boolean> out = new ArrayList<>();
        if(Component.ReadNBT() != null){
            out.add(Component.ReadNBT().getBoolean("Output"));
        }
        else {
            CompoundNBT temp = new CompoundNBT();
            temp.putBoolean("Output",true);
            Component.SetNBT(temp);
            out.add(Component.ReadNBT().getBoolean("Output"));
        }
        return out;
    }


    @Override
    public CompoundNBT OnInteract(ContainerScreen<?> screen, PCBComponentXY<?> Component) {
        CompoundNBT temp = new CompoundNBT();
        if(Component.ReadNBT() != null){
            temp.putBoolean("Output",!Component.ReadNBT().getBoolean("Output"));
        }
        else{
            temp.putBoolean("Output",true);
        }
        return temp;
    }

    @Override
    public void RenderComponent(MatrixStack matrix, int left, int top, ContainerScreen<?> screen,PCBComponentXY<?> componentXY) {
        super.RenderComponent(matrix, left, top, screen, componentXY);
        //render on/off overlay here
        if(componentXY.ReadNBT() != null && componentXY.ReadNBT().getBoolean("Output")){
            screen.blit(matrix, left + 8, top + 4, 0, 48, 8, 8);
        }
        else {
            screen.blit(matrix, left + 8, top + 4, 8, 48, 8, 8);
        }
    }

    @Override
    public void RenderComponent(MatrixStack matrix, int BoundsLeft, int BoundsTop, int BoundsWidth, int BoundsHeight, int RelX, int RelY, ContainerScreen<?> screen, PCBComponentXY<?> componentXY) {
        super.RenderComponent(matrix, BoundsLeft, BoundsTop, BoundsWidth, BoundsHeight, RelX, RelY, screen, componentXY);
        if(componentXY.ReadNBT() != null && componentXY.ReadNBT().getBoolean("Output")){
            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + 8, RelY + 4, BoundsWidth, BoundsHeight, screen, 0, 48, 8, 8);
        }
        else{
            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + 8, RelY + 4, BoundsWidth, BoundsHeight, screen, 8, 48, 8, 8);
        }
    }
}

package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.DefaultPCBComponent;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.nbt.CompoundNBT;

public class PCBComponentXY<T extends DefaultPCBComponent> { //DO NOT EXTEND THIS OR OVERRIDE IT, YOU USE THE FUNCTIONS IN THE PCBCOMPONENT CLASS IF YOU WANT TO SAVE AND LOAD CUSTOM NBT DATA TO HERE
    public int x;
    public int y;
    public T Component;
    public CompoundNBT nbtdata;


    public PCBComponentXY(int X, int Y, T pcbComponent){
        x = X;
        y = Y;
        Component = pcbComponent;
    }

    public void RenderComponent(MatrixStack matrix, int left, int top, ContainerScreen<?> screen) {
        Component.RenderComponent(matrix, left, top, screen, this);
    }
    public void RenderComponent(MatrixStack matrix, int BoundsLeft, int BoundsTop, int BoundsWidth, int BoundsHeight, int RelX, int RelY, ContainerScreen<?> screen) {
        Component.RenderComponent(matrix, BoundsLeft, BoundsTop, BoundsWidth, BoundsHeight, RelX, RelY, screen, this);
    }

    public void SetNBT(CompoundNBT nbt) {
        nbtdata = nbt;
    }

    public CompoundNBT ReadNBT() {
        return nbtdata;
    }

    public void OnInteract(ContainerScreen<?> screen){
        nbtdata = Component.OnInteract(screen,this);
    }
}

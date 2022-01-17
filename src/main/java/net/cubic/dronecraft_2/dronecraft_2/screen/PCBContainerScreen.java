package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public class PCBContainerScreen<T extends Container> extends ContainerScreen<T> {

    public PCBComponentXY<? extends PCBComponent> SelectedComponent = null;


    public PCBContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if(SelectedComponent != null){
            int offset = getBlitOffset();
            setBlitOffset(1000);
            SelectedComponent.Component.RenderComponent(matrixStack,mouseX+SelectedComponent.x ,mouseY+SelectedComponent.y,this);
            setBlitOffset(offset);
        }
    }
}

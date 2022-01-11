package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBMain;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public class PCBContainerScreen<T extends Container> extends ContainerScreen<T> {

    public PCBComponentXY SelectedComponent = null;


    public PCBContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if(SelectedComponent != null){
            PCBRender.RenderPCBComponent(matrixStack,mouseX+SelectedComponent.x ,mouseY+SelectedComponent.y, SelectedComponent.getComponent(),this);
        }
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}

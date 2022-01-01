package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.container.PCBCrafterContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBMain;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PCBCrafterScreen extends ContainerScreen<PCBCrafterContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui.png");
    private final ResourceLocation GUI2 = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui_2.png");

    public PCBCrafterScreen(PCBCrafterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        xSize = 256;
        ySize = 340;
    }

    public PCBComponentXY SelectedComponent = null;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;

        if(SelectedComponent != null){
            PCBRender.RenderPCBComponent(matrixStack,mouseX+SelectedComponent.x ,mouseY+SelectedComponent.y,PCBMain.Components[SelectedComponent.ComponentID],this);
        }

        //RenderSystem.color4f(ModSettings.FBackgroundR(),ModSettings.FBackgroundG(),ModSettings.FBackgroundB(),ModSettings.FBackgroundA());

    }




    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(ModSettings.FBackgroundR(), ModSettings.FBackgroundG(),ModSettings.FBackgroundB(),ModSettings.FBackgroundA());
        int i = this.guiLeft;
        int j = this.guiTop;
        this.minecraft.getTextureManager().bindTexture(GUI2);
        this.blit(matrixStack,i,j,0,0,this.xSize,256);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(matrixStack,i+40,j+173,0,0,176,166);


        //PCBRender.RenderPCBComponent(matrixStack,i+20,j+20, PCBMain.Components[5],this);
        PCBRender.RenderPCB(matrixStack,i + 5,j + 5,PCBMain.PCBdataTest[0],this);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {

    }


    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
    }
}

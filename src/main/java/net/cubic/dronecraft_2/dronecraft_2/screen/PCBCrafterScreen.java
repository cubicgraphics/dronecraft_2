package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.container.PCBCrafterContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBMain;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PCBCrafterScreen extends ContainerScreen<PCBCrafterContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui.png");

    public PCBCrafterScreen(PCBCrafterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;


        RenderSystem.color4f(ModSettings.FBackgroundR(),ModSettings.FBackgroundG(),ModSettings.FBackgroundB(),ModSettings.FBackgroundA());

    }




    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(ModSettings.FBackgroundR(), ModSettings.FBackgroundG(),ModSettings.FBackgroundB(),ModSettings.FBackgroundA());
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack,i,j,0,0,this.xSize,this.ySize);
        PCBRender.RenderPCBComponent(matrixStack,i+20,j+20, PCBMain.Components[5],this);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {

    }


    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);
    }
}

package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.container.PCBEditorContainer;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PCBEditorScreen extends ContainerScreen<PCBEditorContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui.png");

    public PCBEditorScreen(PCBEditorContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }
    private ModSettings S = dronecraft_2Main.modSettings;


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;


        RenderSystem.color4f(S.GetBackgroundValue(0),S.GetBackgroundValue(1),S.GetBackgroundValue(2),S.GetBackgroundValue(3));

    }




    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(S.BackgroundGUIColourRGBA[0],S.BackgroundGUIColourRGBA[1],S.BackgroundGUIColourRGBA[2], S.BackgroundGUIColourRGBA[3]);   //works to re-colour anything set below it here. can be stacked to change separately blit textures
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack,i,j,0,0,this.xSize,this.ySize);
    }
}

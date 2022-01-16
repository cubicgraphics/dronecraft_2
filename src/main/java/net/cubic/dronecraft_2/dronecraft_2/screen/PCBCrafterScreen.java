package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.container.PCBCrafterContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponents;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBComponentRecipe;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBRecipeTypes;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import net.minecraftforge.fml.client.gui.widget.Slider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PCBCrafterScreen extends PCBContainerScreen<PCBCrafterContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui.png");
    private final ResourceLocation GUI2 = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui_2.png");

    ExtendedButton SavePCBButton;
    ExtendedButton TestButton;


    public PCBCrafterScreen(PCBCrafterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        xSize = 256;
        ySize = 340;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;
        PCBRender.RenderPCBTooltips(matrixStack, container.CurrentPCB, mouseX, mouseY,this.guiLeft + container.LeftPCBGrid,this.guiTop + container.TopPCBGrid,this);
        PCBRender.RenderSelectablePCBComponentTooltips(matrixStack,container.SelectablePCBComponents,mouseX,mouseY, container.LeftPCBSelectionBar, container.TopPCBSelectionBar, container.ScrollOffset, container.PCBSelectionBarWidth, container.PCBSelectionBarHeight, this);




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
        PCBRender.RenderSelectablePCBComponents(matrixStack,i + container.LeftPCBSelectionBar,j +container.TopPCBSelectionBar,  container.ScrollOffset, container.PCBSelectionBarWidth,container.PCBSelectionBarHeight, container.GetSelectablePCBComponentsList(),this);
        PCBRender.RenderPCB(matrixStack,i + container.LeftPCBGrid,j + container.TopPCBGrid, container.SetAndGetPCBDataFromItem(), this);
        if(container.CurrentPCB != null){
            if(container.CurrentPCB.width > container.PCBMaxTileWidth && container.CurrentPCB.length > container.PCBMaxTileLength){
                //render max size text here
                this.font.drawString(matrixStack,"The PCB is too detailed to edit by hand",17,3,new RGBA(255,0,0).ToRawInt());
            }
        }

    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {

    }


    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);

        SavePCBButton = new ExtendedButton(this.guiLeft + 165, this.guiTop + 111, 70, 20, ITextComponent.getTextComponentOrEmpty("Solder"), button -> SavePCBButtonPressed());
        TestButton= new ExtendedButton(this.guiLeft + 165, this.guiTop + 130, 70, 20, ITextComponent.getTextComponentOrEmpty("Test"), button -> container.UpdateSelectablePCBComponentsAndWires());

        Slider slide = new Slider(this.guiLeft + 16, this.guiTop + 6, 130, 9,
                ITextComponent.getTextComponentOrEmpty("Scroll"),ITextComponent.getTextComponentOrEmpty(""),
                0,1, 0,
                true,false, null,onPress());

        addButton(SavePCBButton);
        addButton(TestButton);
        addButton(slide);
    }

    protected final Slider.ISlider onPress(){
        return slider -> {
            if(container.PCBSelectionBarWidth < container.SelectablePCBComponentsPixelWidth){
                container.ScrollOffset = (int)(slider.getValue()*(container.SelectablePCBComponentsPixelWidth-container.PCBSelectionBarWidth));
            }
        };
    }


    public void SavePCBButtonPressed(){
        System.out.println("ButtonPressed");
        //TODO only take items from right grid once this button is pressed. Display all the required item components below the button or some way to diaplay whats needed
        container.SavePCBToItem();
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
        //SelectedComponent = new PCBComponentXY();
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }
}

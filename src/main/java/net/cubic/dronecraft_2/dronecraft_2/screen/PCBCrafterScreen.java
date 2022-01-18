package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.container.PCBCrafterContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import net.minecraftforge.fml.client.gui.widget.Slider;

public class PCBCrafterScreen extends PCBContainerScreen<PCBCrafterContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui.png");
    private final ResourceLocation GUI2 = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui_2.png");

    VarType SelectedWireType;
    int SelectedWireTypeListIndex = -1;
    ExtendedButton SavePCBButton;

    public PCBCrafterScreen(PCBCrafterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        xSize = 256;
        ySize = 340;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        PCBRender.RenderPCBTooltips(matrixStack, container.CurrentPCB, mouseX, mouseY,this.guiLeft + container.LeftPCBGrid,this.guiTop + container.TopPCBGrid,this);
        PCBRender.RenderSelectablePCBComponentTooltips(matrixStack,container.SelectablePCBComponents,mouseX,mouseY, i +container.LeftPCBSelectionBar,j +  container.TopPCBSelectionBar, container.SelectBoxScrollOffsetX, container.SelectBoxScrollOffsetY, container.PCBSelectionBarWidth, container.PCBSelectionBarHeight, this);
        PCBRender.RenderSelectableWireComponentTooltips(matrixStack,container.SelectablePCBWires,mouseX,mouseY,i+ container.LeftWireSelectionBar,j+ container.TopWireSelectionBar, container.SelectableWireScrollOffsetX, container.SelectableWireBarWidth, container.SelectableWireBarHeight, this);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(ModSettings.FBackgroundR(), ModSettings.FBackgroundG(),ModSettings.FBackgroundB(),ModSettings.FBackgroundA());
        int i = this.guiLeft;
        int j = this.guiTop;
        this.minecraft.getTextureManager().bindTexture(GUI2);
        this.blit(matrixStack,i,j,0,0,this.xSize,256);
        this.minecraft.getTextureManager().bindTexture(GUI);
        this.blit(matrixStack,i+40,j+256,0,84,176,84);

        PCBRender.RenderSelectableWires(matrixStack,i + container.LeftWireSelectionBar,j + container.TopWireSelectionBar, container.SelectableWireScrollOffsetX, container.SelectableWireBarWidth, container.SelectableWireBarHeight, container.SelectablePCBWires,this, SelectedWireTypeListIndex);
        PCBRender.RenderSelectablePCBComponents(matrixStack,i + container.LeftPCBSelectionBar,j +container.TopPCBSelectionBar, container.SelectBoxScrollOffsetX, container.SelectBoxScrollOffsetY, container.PCBSelectionBarWidth, container.PCBSelectionBarHeight, container.GetSelectablePCBComponentsList(),this);
        PCBRender.RenderPCB(matrixStack,i + container.LeftPCBGrid,j + container.TopPCBGrid, container.SetAndGetPCBDataFromItem(), this);
        if(container.CurrentPCB != null){
            if(container.CurrentPCB.width > container.PCBMaxTileWidth && container.CurrentPCB.length > container.PCBMaxTileLength){
                this.font.drawString(matrixStack,"The PCB is too detailed to edit by hand, max size of 16x16",17,3,new RGBA(255,0,0).ToRawInt());
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
        Slider slide = new Slider(this.guiLeft + 16, this.guiTop + 6, 130, 9,
                ITextComponent.getTextComponentOrEmpty("Scroll"),ITextComponent.getTextComponentOrEmpty(""),
                0,1, 0,
                true,false, null,onPress());
        Slider slide2 = new Slider(this.guiLeft + 17, this.guiTop + 73, 128, 6,
                ITextComponent.getTextComponentOrEmpty("Scroll"),ITextComponent.getTextComponentOrEmpty(""),
                0,1, 0,
                true,false, null,onPress2());
        addButton(SavePCBButton);
        addButton(slide);
        addButton(slide2);
    }

    protected final Slider.ISlider onPress(){
        return slider -> {
            if(container.PCBSelectionBarWidth < container.SelectablePCBComponentsPixelWidth){
                container.SelectBoxScrollOffsetX = (int)(slider.getValue()*(container.SelectablePCBComponentsPixelWidth-container.PCBSelectionBarWidth));
            }
        };
    }
    protected final Slider.ISlider onPress2(){
        return slider -> {
            if(container.SelectableWireBarWidth < container.SelectableWiresPixelWidth){
                container.SelectableWireScrollOffsetX = (int)(slider.getValue()*(container.SelectableWiresPixelWidth-container.SelectableWireBarWidth));
            }
        };
    }

    public void SavePCBButtonPressed(){
        System.out.println("ButtonPressed");
        //TODO only take items from right grid once this button is pressed. Display all the required item components below the button or some way to display whats needed
        container.SavePCBToItem();
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.guiLeft;
        int j = this.guiTop;
        if (mouseX >= container.LeftPCBSelectionBar + i && mouseY >= container.TopPCBSelectionBar + j && mouseX < container.LeftPCBSelectionBar + container.PCBSelectionBarWidth + i && mouseY < container.TopPCBSelectionBar+ container.PCBSelectionBarHeight + j && SelectedComponent == null)
        {
            if(container.SelectBoxScrollOffsetX - (int)dragX >= 0 && container.SelectBoxScrollOffsetX - (int)dragX <= container.SelectablePCBComponentsPixelWidth - container.PCBSelectionBarWidth){
                container.SelectBoxScrollOffsetX = container.SelectBoxScrollOffsetX - (int)dragX;
            }
            if(container.SelectBoxScrollOffsetY - (int)dragY >= 0 && container.SelectBoxScrollOffsetY - (int)dragY <= container.SelectablePCBComponentsPixelHeight - container.PCBSelectionBarHeight){
                container.SelectBoxScrollOffsetY = container.SelectBoxScrollOffsetY - (int)dragY;
            }
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }




    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = this.guiLeft;
        int j = this.guiTop;
        if(mouseX >= i+ container.LeftWireSelectionBar && mouseX <= i+ container.LeftWireSelectionBar + container.SelectableWireBarWidth && mouseY >= j+ container.TopWireSelectionBar && mouseY <= j+ container.TopWireSelectionBar + container.SelectableWireBarHeight){
            for (int l = 0; l < container.SelectablePCBWires.size(); l++) {
                if (container.SelectablePCBWires.get(l) != null
                        && ((mouseX >= l*8 + i+ container.LeftWireSelectionBar - container.SelectableWireScrollOffsetX)
                        && (mouseX < (l+1)*8 + i+ container.LeftWireSelectionBar - container.SelectableWireScrollOffsetX))
                        && (mouseY <= j+ container.TopWireSelectionBar+8)){
                    if(SelectedWireTypeListIndex == l){
                        SelectedWireTypeListIndex = -1;
                        SelectedWireType = null;
                    }
                    else{
                        SelectedWireType = container.SelectablePCBWires.get(l);
                        SelectedWireTypeListIndex = l;
                    }
                    return true;
                }
            }
            if(SelectedComponent != null){
                SelectedComponent = null;
                return true;
            }
        }
        if(mouseX >= i+ container.LeftPCBSelectionBar && mouseX <= i+ container.LeftPCBSelectionBar + container.PCBSelectionBarWidth && mouseY >= j+ container.TopPCBSelectionBar && mouseY <= j+ container.TopWireSelectionBar + container.PCBSelectionBarHeight){
            if(SelectedComponent != null){
                SelectedComponent = null;
                return true;
            }
            else{
                for (int k = 0; k < container.SelectablePCBComponents.size(); k++) {

                    if ((container.SelectablePCBComponents.get(k) != null)
                            && (mouseX >= i+ container.LeftPCBSelectionBar - container.SelectBoxScrollOffsetX + container.SelectablePCBComponents.get(k).x)
                            && (mouseX < i+ container.LeftPCBSelectionBar - container.SelectBoxScrollOffsetX + container.SelectablePCBComponents.get(k).x + container.SelectablePCBComponents.get(k).Component.Length*8)// Length is X in the case of components
                            && (mouseY >= j+ container.TopPCBSelectionBar - container.SelectBoxScrollOffsetY + container.SelectablePCBComponents.get(k).y)
                            && (mouseY < j+ container.TopPCBSelectionBar - container.SelectBoxScrollOffsetY + container.SelectablePCBComponents.get(k).y + container.SelectablePCBComponents.get(k).Component.Width*8))
                            {
                                SelectedComponent = new PCBComponentXY<>((int) (i + container.LeftPCBSelectionBar - container.SelectBoxScrollOffsetX + container.SelectablePCBComponents.get(k).x - mouseX), (int) (j + container.TopPCBSelectionBar - container.SelectBoxScrollOffsetY + container.SelectablePCBComponents.get(k).y - mouseY), container.SelectablePCBComponents.get(k).Component);
                                return true;
                            }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }
}

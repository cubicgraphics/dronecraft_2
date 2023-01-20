package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.container.PCBCrafterContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.DefaultPCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import net.minecraftforge.fml.client.gui.widget.Slider;

import java.util.List;

public class PCBCrafterScreen extends PCBContainerScreen<PCBCrafterContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui.png");
    private final ResourceLocation GUI2 = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_editor_gui_2.png");

    VarType SelectedWireType;
    int SelectedWireTypeListIndex = -1;
    ExtendedButton SavePCBButton;
    TextFieldWidget SearchBox;


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
        RenderItemList(container.ItemsForRecipe,i+164,j+132,4);

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
        SearchBox = new TextFieldWidget(font,this.guiLeft + 16,this.guiTop - 30, 130,20,ITextComponent.getTextComponentOrEmpty("Search components"));

        addButton(SearchBox);
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

        //TODO take items from right grid once this button is pressed.
        container.SavePCBToItem();
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        int i = this.guiLeft;
        int j = this.guiTop;
        if (mouseX >= container.LeftPCBSelectionBar + i && mouseY >= container.TopPCBSelectionBar + j && mouseX < container.LeftPCBSelectionBar + container.PCBSelectionBarWidth + i && mouseY < container.TopPCBSelectionBar+ container.PCBSelectionBarHeight + j && SelectedComponent == null){
            if(container.SelectBoxScrollOffsetX - (int)dragX >= 0 && container.SelectBoxScrollOffsetX - (int)dragX <= container.SelectablePCBComponentsPixelWidth - container.PCBSelectionBarWidth){
                container.SelectBoxScrollOffsetX = container.SelectBoxScrollOffsetX - (int)dragX;
                return true;
            }
            if(container.SelectBoxScrollOffsetY - (int)dragY >= 0 && container.SelectBoxScrollOffsetY - (int)dragY <= container.SelectablePCBComponentsPixelHeight - container.PCBSelectionBarHeight){
                container.SelectBoxScrollOffsetY = container.SelectBoxScrollOffsetY - (int)dragY;
                return true;
            }
        }
        else if(mouseX >= i+ container.LeftPCBGrid && mouseX <= i+ container.LeftPCBGrid + container.PCBGridTileWidth*8 && mouseY >= j + container.TopPCBGrid && mouseY <= j+ container.TopPCBGrid + container.PCBGridTileLength*8 && container.CurrentPCB != null) {
            if(SelectedWireType != null && (Math.abs(dragX) > 1 || Math.abs(dragY) > 1)){
                int x = Math.floorDiv((int) (mouseX - (i + container.LeftPCBGrid)),8);
                int y = Math.floorDiv((int) (mouseY - (j + container.TopPCBGrid)),8);
                container.CurrentPCB.PCBWiresArray[x][y] = SelectedWireType;
                return true;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
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
                }
            }
            if(SelectedComponent != null){
                SelectedComponent = null;
            }
        }
        if(mouseX >= i + container.LeftPCBSelectionBar && mouseX <= i + container.LeftPCBSelectionBar + container.PCBSelectionBarWidth && mouseY >= j + container.TopPCBSelectionBar && mouseY <= j + container.TopPCBSelectionBar + container.PCBSelectionBarHeight){
            if(SelectedComponent != null){
                SelectedComponent = null;
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
                            }
                }
            }
        }
        if(mouseX >= i+ container.LeftPCBGrid && mouseX <= i+ container.LeftPCBGrid + container.PCBGridTileWidth*8 && mouseY >= j + container.TopPCBGrid && mouseY <= j+ container.TopPCBGrid + container.PCBGridTileLength*8 && container.CurrentPCB != null){
            if(SelectedComponent != null){
                int x = Math.floorDiv((int) (mouseX - (i + container.LeftPCBGrid - SelectedComponent.x)),8);
                int y = Math.floorDiv((int) (mouseY - (j + container.TopPCBGrid - SelectedComponent.y)),8);
                PCBComponentXY<? extends DefaultPCBComponent> component = new PCBComponentXY<>(x,y,SelectedComponent.Component);
                component.SetNBT(SelectedComponent.nbtdata);
                container.CurrentPCB.ComponentList.add(component);
                SelectedComponent = null;
                container.UpdateIngredientsList();
                return true;
            }
            else if(SelectedWireType != null){
                int x = Math.floorDiv((int) (mouseX - (i + container.LeftPCBGrid)),8);
                int y = Math.floorDiv((int) (mouseY - (j + container.TopPCBGrid)),8);
                if(container.CurrentPCB.PCBWiresArray[x][y] != null){
                    container.CurrentPCB.PCBWiresArray[x][y] = null;
                    container.UpdateIngredientsList();
                }
                else{
                    container.CurrentPCB.PCBWiresArray[x][y] = SelectedWireType;
                    container.UpdateIngredientsList();
                }
                return true;
            }
            else{
                for (int k = 0; k < container.CurrentPCB.ComponentList.size(); k++) {
                    if (((mouseX > i + container.LeftPCBGrid + container.CurrentPCB.ComponentList.get(k).x*8) && (mouseX < i + container.LeftPCBGrid + container.CurrentPCB.ComponentList.get(k).x*8 +container.CurrentPCB.ComponentList.get(k).Component.Length*8))
                            && ((mouseY > j + container.TopPCBGrid + container.CurrentPCB.ComponentList.get(k).y*8) && (mouseY < j + container.TopPCBGrid + container.CurrentPCB.ComponentList.get(k).y*8 +container.CurrentPCB.ComponentList.get(k).Component.Width*8))){
                        if(Screen.hasShiftDown()){
                            container.CurrentPCB.ComponentList.get(k).OnInteract(this);
                        }
                        else if(Screen.hasAltDown()){
                            container.CurrentPCB.ComponentList.remove(k);
                            container.UpdateIngredientsList();
                        }
                        else{
                            SelectedComponent = new PCBComponentXY<>((int) (i + container.LeftPCBGrid+ container.CurrentPCB.ComponentList.get(k).x*8 - mouseX), (int) (j + container.TopPCBGrid + container.CurrentPCB.ComponentList.get(k).y*8 - mouseY), container.CurrentPCB.ComponentList.get(k).Component);
                            SelectedComponent.SetNBT(container.CurrentPCB.ComponentList.get(k).nbtdata);
                            if(!Screen.hasControlDown()){
                                container.CurrentPCB.ComponentList.remove(k);
                                container.UpdateIngredientsList();
                            }
                        }
                        return true;
                    }
                }
            }


        }


        return super.mouseClicked(mouseX, mouseY, button);

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(SearchBox.isFocused() && keyCode == 69){
            return true;
        }
        else{
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        container.UpdateSelectablePCBComponentsAndWires(SearchBox.getText());
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        return super.mouseReleased(mouseX, mouseY, button);
    }



    int ItemTimer = 0;
    public void RenderItemList(List<ItemStack[]> itemStacks, int left, int top, int ItemWidth) {
        if (itemStacks != null) {
            ItemTimer ++;
            int dx = 18;
            int dy = 18;
            int xOffset = 0;
            int yOffset = 0;
            for (ItemStack[] itemStack : itemStacks) {
                int i = itemStack.length - ((ItemTimer/20) % itemStack.length)-1;
                net.minecraft.client.gui.FontRenderer font = itemStack[i].getItem().getFontRenderer(itemStack[i]);
                if (font == null) font = this.font;
                this.itemRenderer.renderItemAndEffectIntoGUI(itemStack[i].getStack(), left + xOffset, top + yOffset);
                this.itemRenderer.renderItemOverlayIntoGUI(font,itemStack[i],left + xOffset, top + yOffset,null);
                xOffset += dx;
                if (xOffset >= dx * ItemWidth) {
                    xOffset = 0;
                    yOffset += dy;
                }
            }
        }
    }
}

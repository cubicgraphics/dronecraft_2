package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBSymbol;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCB_IO;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.cubic.dronecraft_2.dronecraft_2.screen.PCBRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

/**
 * Extend this to make more PCB components
 *
 */
public class DefaultPCBComponent extends net.minecraftforge.registries.ForgeRegistryEntry<DefaultPCBComponent> implements IPCBComponent {

    //TODO add an ENUM for whether the component is an input, an output, or a process. if its an input or an output then the PCB will use it as an input etc... EG: block location sensor, or constant variable output, or etc...
    public enum TYPE{
        INPUT,
        PROCESS,
        OUTPUT
    }


    //A component is a part of the PCB - they get dragged into place in the builder gui
    public int Length; //x  Size it will take on the PCB designer
    public int Width; //y
    public PCB_IO[] Inputs;
    public PCB_IO[] Outputs;
    public RGBA ComponentColor;
    public PCBSymbol[] Decals;
    public String translationKey;
    public TYPE Type;
    private CompoundNBT nbtdata;

    public DefaultPCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color,TYPE type){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Decals = null;
        Type = type;
    }

    public DefaultPCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol decal,TYPE type){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Decals = new PCBSymbol[]{
                decal
        };
        Type = type;
    }
    public DefaultPCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol[] decals,TYPE type){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Decals = decals;
        Type = type;
    }


    public ITextComponent getName() {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    /**
     * Override this for custom pcb rendering - eg if the custom pcb holds an item or number filter that may want rendering
     */
    public void RenderComponent(MatrixStack matrix, int left, int top, ContainerScreen<?> screen) {
        ResourceLocation TEXTURE = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_components.png");
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        int length = this.Length;
        int width = this.Width;
        //draw inputs
        RenderSystem.color4f(1f, 1f, 0.1f,1f);
        for (PCB_IO input : this.Inputs) {
            RenderSystem.color4f(input.DataType.getColor().r, input.DataType.getColor().g, input.DataType.getColor().b,input.DataType.getColor().a);
            screen.blit(matrix, left + (input.X * 8), top + (input.Y * 8), 0, 8, 8, 8);
        }
        //draw outputs
        for (PCB_IO output : this.Outputs) {

            RenderSystem.color4f(output.DataType.getColor().r, output.DataType.getColor().g, output.DataType.getColor().b,output.DataType.getColor().a);
            screen.blit(matrix, left + (output.X * 8), top + (output.Y * 8), 0, 8, 8, 8);
        }
        //draw pcb body
        RenderSystem.color4f(this.ComponentColor.r, this.ComponentColor.g,this.ComponentColor.b,this.ComponentColor.a);
        screen.blit(matrix,left,top,32,8,8,8);
        for (int x = 1; x < length - 1; x++) {
            screen.blit(matrix,left +(x*8) ,top,8,0,8,8);
        }
        screen.blit(matrix,left +((length-1)*8),top,8,8,8,8);
        screen.blit(matrix,left,top + ((width-1)*8),24,8,8,8);
        for (int x = 1; x < length - 1; x++) {
            screen.blit(matrix,left +(x*8) ,top+ ((width-1)*8),24,0,8,8);
        }
        screen.blit(matrix,left+ ((length-1)*8),top+ ((width-1)*8),16,8,8,8);
        for (int y = 1; y < width - 1; y++) {
            screen.blit(matrix,left  ,top+(y*8),32,0,8,8);
        }
        for (int y = 1; y < width - 1; y++) {
            screen.blit(matrix,left + ((length-1) * 8) ,top+(y*8),16,0,8,8);
        }
        for (int x = 1; x < length - 1; x++) {
            for (int y = 1; y < width - 1; y++) {
                screen.blit(matrix,left+ (x*8),top+ (y*8),0,0,8,8);
            }
        }
        if(this.Decals != null){
            for (PCBSymbol decal : this.Decals) {
                screen.blit(matrix, left + (int) (decal.X * 8), top + (int) (decal.Y * 8), decal.OffsetX, decal.OffsetY, decal.SizeX, decal.SizeY);
            }
        }
        //filler screen.blit(matrix,left,top,0,0,8,8);
        //connectionPoint screen.blit(matrix,left,top,0,8,8,8); //color this differently

        //top screen.blit(matrix,left,top,8,0,8,8);
        //Right screen.blit(matrix,left,top,16,0,8,8);
        //bottom screen.blit(matrix,left,top,24,0,8,8);
        //Left screen.blit(matrix,left,top,32,0,8,8);

        //topRight screen.blit(matrix,left,top,8,8,8,8);
        //bottomLeft screen.blit(matrix,left,top,24,8,8,8);
        //bottomRight screen.blit(matrix,left,top,16,8,8,8);
        //topLeft screen.blit(matrix,left,top,32,8,8,8);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void RenderComponent(MatrixStack matrix, int BoundsLeft, int BoundsTop, int BoundsWidth, int BoundsHeight, int RelX, int RelY, ContainerScreen<?> screen) {

        ResourceLocation TEXTURE = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_components.png");
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        int length = this.Length;
        int width = this.Width;
        //draw inputs
        for (PCB_IO input : this.Inputs) {
            RenderSystem.color4f(input.DataType.getColor().r, input.DataType.getColor().g, input.DataType.getColor().b,input.DataType.getColor().a);
            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + (input.X * 8), RelY + (input.Y * 8), BoundsWidth, BoundsHeight, screen, 0, 8, 8, 8);
        }
        //draw outputs
        for (PCB_IO output : this.Outputs) {
            RenderSystem.color4f(output.DataType.getColor().r, output.DataType.getColor().g, output.DataType.getColor().b,output.DataType.getColor().a);

            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + (output.X * 8), RelY + (output.Y * 8), BoundsWidth, BoundsHeight, screen, 0, 8, 8, 8);
        }
        //draw pcb body
        RenderSystem.color4f(this.ComponentColor.r, this.ComponentColor.g, this.ComponentColor.b, this.ComponentColor.a);
        PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX, RelY, BoundsWidth, BoundsHeight, screen, 32, 8, 8, 8);
        for (int x = 1; x < length - 1; x++) {
            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + (x * 8), RelY, BoundsWidth, BoundsHeight, screen, 8, 0, 8, 8);
        }
        PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + ((length - 1) * 8), RelY, BoundsWidth, BoundsHeight, screen, 8, 8, 8, 8);
        PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX, RelY + ((width - 1) * 8), BoundsWidth, BoundsHeight, screen, 24, 8, 8, 8);
        for (int x = 1; x < length - 1; x++) {
            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + (x * 8), RelY + ((width - 1) * 8), BoundsWidth, BoundsHeight, screen, 24, 0, 8, 8);
        }
        PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + ((length - 1) * 8), RelY + ((width - 1) * 8), BoundsWidth, BoundsHeight, screen, 16, 8, 8, 8);
        for (int y = 1; y < width - 1; y++) {
            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX, RelY + (y * 8), BoundsWidth, BoundsHeight, screen, 32, 0, 8, 8);
        }
        for (int y = 1; y < width - 1; y++) {
            PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + ((length - 1) * 8), RelY + (y * 8), BoundsWidth, BoundsHeight, screen, 16, 0, 8, 8);
        }
        for (int x = 1; x < length - 1; x++) {
            for (int y = 1; y < width - 1; y++) {
                PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + (x * 8), RelY + (y * 8), BoundsWidth, BoundsHeight, screen, 0, 0, 8, 8);
            }
        }
        if (this.Decals != null) {
            for (PCBSymbol decal : this.Decals) {
                PCBRender.BlitWithClipping(matrix, BoundsLeft, BoundsTop, RelX + (int) (decal.X * 8), RelY + (int) (decal.Y * 8), BoundsWidth, BoundsHeight, screen, decal.OffsetX, decal.OffsetY, decal.SizeX, decal.SizeY);
            }
        }
    }

    public String getDefaultTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("pcb_component", GameRegistry.findRegistry(DefaultPCBComponent.class).getKey(this));
        }

        return this.translationKey;
    }

    /**
     * Returns the unlocalized name of this item.
     */
    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }



    /**
     * Save NBT data for custom components
     * Override it to save custom variables for components as this data will be saved inside the PCB
     * Custom var can be used for example save an item to the component then possibly render the item as part of the component
     */
    @Override
    public void SaveCustomVarToNBT() {

    }
    /**
     * Read NBT data for custom components
     * Override it to Read custom variables for components as this data will be saved inside the PCB
     */
    @Override
    public void ReadNBTToCustomVar() {

    }

    public void SetNBT(CompoundNBT nbt) {
        nbtdata = nbt;
    }
    public CompoundNBT ReadNBT() {
        return nbtdata;
    }


    /**
     * returns null if the inputs dont match the varTypes defined, else it will return the values specified in CalculateOutput
     * @param inputs
     * @return
     */
    private List<?> GetOutput(List<?> inputs){
        if(CheckDataEquals(inputs)){ //use enum to check if it is an input or an output or a process (process has both)
            return CalculateOutput(inputs);
        }
        else{
            return null;
        }
    }

    /**
     * Override ME!
     * If this is an input(defined by the enum) then just return what you want it ot output. (The input will be null).
     * If this is an output(defined by the enum) then this should be set to return null, only the data types inputted are checked for the correct class type.
     * If this is a process(defined by the enum) then this should use the inputs to return an output.
     * MAKE SURE YOU OUTPUT THE CORRECT VAR TYPES YOU HAVE DEFINED
     *
     * @param inputs
     * @return outputs
     */
    @Override
    public List<?> CalculateOutput(List<?> inputs){

        return null;
    }

    /**
     * checks the input data has the correct data types
     *
     * @param inputs
     * @return
     */
    private boolean CheckDataEquals(List<?> inputs){
        if(inputs.size() == Inputs.length){
            boolean check = true;
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i).getClass() != Inputs[i].DataType.getDataType()) {
                    check = false;
                    break;
                }
            }
            return check;
        }
        else{
            return false;
        }
    }

}

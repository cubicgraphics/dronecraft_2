package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PCBComponent extends net.minecraftforge.registries.ForgeRegistryEntry<PCBComponent> {

    //A component is a part of the PCB - they get dragged into place in the builder gui
    public int Length; //x  Size it will take on the PCB designer
    public int Width; //y
    public PCB_IO[] Inputs;
    public PCB_IO[] Outputs;
    public RGBA ComponentColor;
    public String Instruction;
    public PCBSymbol[] Decals;
    public String translationKey;


    public PCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = null;
        Decals = null;
    }

    public PCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, String instruction){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = instruction;
        Decals = null;
    }

    public PCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol decal){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = null;
        Decals = new PCBSymbol[]{
                decal
        };
    }
    public PCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, String instruction, PCBSymbol decal){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = instruction;
        Decals = new PCBSymbol[]{
                decal
        };
    }
    public PCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, PCBSymbol[] decals){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = null;
        Decals = decals;
    }

    public PCBComponent(int length, int width, PCB_IO[] inputs, PCB_IO[] outputs, RGBA color, String instruction, PCBSymbol[] decals){
        Length = length;
        Width = width;
        Inputs = inputs;
        Outputs = outputs;
        ComponentColor = color;
        Instruction = instruction;
        Decals = decals;
    }


    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName() {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    protected String getDefaultTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("pcb_component", GameRegistry.findRegistry(PCBComponent.class).getKey(this));
        }

        return this.translationKey;
    }

    /**
     * Returns the unlocalized name of this item.
     */
    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }
}

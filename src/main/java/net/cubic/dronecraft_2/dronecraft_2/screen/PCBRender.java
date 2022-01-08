package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBMain;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Arrays;
import java.util.List;

public class PCBRender {
    // only call these functions from within a gui screen

    public static void RenderPCB(MatrixStack matrix, int left, int top, PCBData PCB, ContainerScreen<?> screen){
        if(PCB != null){
            ResourceLocation TEXTURE = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_components.png");
            Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
            //should draw wires if there are any
            RenderSystem.color4f(1.0F, 1.0F, 0.0F,1.0F);
            if (PCB.PCBWiresArray != null){
                for (int x = 0; x < PCB.length - 1; x++) {
                    for (int y = 0; y < PCB.width - 1; y++) {
                        if(PCB.PCBWiresArray[x][y] == 1){
                            screen.blit(matrix,left+ (x*8),top+ (y*8),0,0,8,8);
                        }
                    }
                }
            }
            //For drawing an outline
            //for the corners
            RenderSystem.color4f(1.0F, 1.0F, 1.0F,1.0F);
            screen.blit(matrix,left,top,64,8,8,8);
            screen.blit(matrix,left +((PCB.length-1)*8),top,40,8,8,8);
            screen.blit(matrix,left,top + ((PCB.width-1)*8),56,8,8,8);
            screen.blit(matrix,left+ ((PCB.length-1)*8),top+ ((PCB.width-1)*8),48,8,8,8);

            //top row
            for (int x = 1; x < PCB.length - 1; x++) {
                screen.blit(matrix,left +(x*8) ,top,40,0,8,8);
            }
            //bottom row
            for (int x = 1; x < PCB.length - 1; x++) {
                screen.blit(matrix,left +(x*8) ,top+ ((PCB.width-1)*8),56,0,8,8);
            }
            //left down row
            for (int y = 1; y < PCB.width - 1; y++) {
                screen.blit(matrix,left  ,top+(y*8),64,0,8,8);
            }
            //right bottom down
            for (int y = 1; y < PCB.width - 1; y++) {
                screen.blit(matrix,left + ((PCB.length-1) * 8) ,top+(y*8),48,0,8,8);
            }

            //next render the components on top of the wires
            for (int i = 0; i < PCB.ComponentArray.length; i++){
                RenderPCBComponent(matrix,left+(PCB.ComponentArray[i].x*8),top + (PCB.ComponentArray[i].y*8),PCBMain.Components[PCB.ComponentArray[i].ComponentID],screen);
            }
        }

        //top screen.blit(matrix,left,top,40,0,8,8);
        //Right screen.blit(matrix,left,top,48,0,8,8);
        //bottom screen.blit(matrix,left,top,56,0,8,8);
        //Left screen.blit(matrix,left,top,64,0,8,8);

        //topRight screen.blit(matrix,left,top,40,8,8,8);
        //bottomLeft screen.blit(matrix,left,top,56,8,8,8);
        //bottomRight screen.blit(matrix,left,top,48,8,8,8);
        //topLeft screen.blit(matrix,left,top,64,8,8,8);
    }

    public static void RenderPCBComponent(MatrixStack matrix, int left, int top, PCBComponent Component, ContainerScreen<?> screen){
        ResourceLocation TEXTURE = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_components.png");
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
        int length = Component.Length;
        int width = Component.Width;
        //draw inputs
        RenderSystem.color4f(1.0F, 170F/255F, 0.0F,1.0F);
        for (int i = 0; i < Component.Inputs.length; i++) {
            screen.blit(matrix,left +(Component.Inputs[i].X*8) ,top+(Component.Inputs[i].Y*8),0,8,8,8);
        }
        //draw outputs
        RenderSystem.color4f(1.0F, 200F/255F, 0.0F,1.0F);
        for (int i = 0; i < Component.Outputs.length; i++) {
            screen.blit(matrix,left +(Component.Outputs[i].X*8) ,top+(Component.Outputs[i].Y*8),0,8,8,8);
        }
        //draw pcb body
        RenderSystem.color4f(Component.ComponentColor.r, Component.ComponentColor.g,Component.ComponentColor.b,Component.ComponentColor.a);
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
        if(Component.Decals != null){
            for (int i = 0; i < Component.Decals.length; i++) {
                screen.blit(matrix,left+ (int)(Component.Decals[i].X*8),top+ (int)(Component.Decals[i].Y*8),Component.Decals[i].OffsetX,Component.Decals[i].OffsetY,Component.Decals[i].SizeX,Component.Decals[i].SizeY);
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

    public static void RenderPCBTooltips(MatrixStack matrix, PCBData PCB, int MouseX, int MouseY,int left, int top, ContainerScreen<?> screen){

        for (int i = 0; i < PCB.ComponentArray.length; i++) {
            if (((MouseX > left + PCB.ComponentArray[i].x*8) && (MouseX < left + PCB.ComponentArray[i].x*8 +PCBMain.Components[PCB.ComponentArray[i].ComponentID].Length*8))
            && ((MouseY > top + PCB.ComponentArray[i].y*8) && (MouseY < top + PCB.ComponentArray[i].y*8 +PCBMain.Components[PCB.ComponentArray[i].ComponentID].Width*8)))
            {
                screen.renderTooltip(matrix, ITextComponent.getTextComponentOrEmpty(PCBMain.Components[PCB.ComponentArray[i].ComponentID].ComponentName),MouseX,MouseY);
            }
        }
    }

    //TODO add in code for this function for rendering a scrollable PCB also make it click and hold to pan possibly??
    public static void RenderPCBTooltips(MatrixStack matrix, PCBData PCB, int MouseX, int MouseY,int left, int top, ContainerScreen<?> screen, int TileLength, int TileWidth, int scrollX, int scrollY){

        for (int i = 0; i < PCB.ComponentArray.length; i++) {
            if (((MouseX > left + PCB.ComponentArray[i].x*8) && (MouseX < left + PCB.ComponentArray[i].x*8 +PCBMain.Components[PCB.ComponentArray[i].ComponentID].Length*8))
                    && ((MouseY > top + PCB.ComponentArray[i].y*8) && (MouseY < top + PCB.ComponentArray[i].y*8 +PCBMain.Components[PCB.ComponentArray[i].ComponentID].Width*8)))
            {
                screen.renderTooltip(matrix, ITextComponent.getTextComponentOrEmpty(PCBMain.Components[PCB.ComponentArray[i].ComponentID].ComponentName),MouseX,MouseY);
            }
        }
    }



    public static void RenderPCBComponentTooltips(MatrixStack matrix, List<PCBComponentXY> Components, int MouseX, int MouseY, ContainerScreen<?> screen){

        for (int i = 0; i < Components.size(); i++) {
            if (Components.get(i) != null && ((MouseX >= Components.get(i).x)
                    && (MouseX <= Components.get(i).x + PCBMain.Components[Components.get(i).ComponentID].Length))
                    && ((MouseY >= Components.get(i).y)
                    && (MouseY <= Components.get(i).y + PCBMain.Components[Components.get(i).ComponentID].PCBData.width))){
                screen.renderTooltip(matrix, ITextComponent.getTextComponentOrEmpty(PCBMain.Components[Components.get(i).ComponentID].ComponentName),MouseX,MouseY);

            }
        }
    }
}

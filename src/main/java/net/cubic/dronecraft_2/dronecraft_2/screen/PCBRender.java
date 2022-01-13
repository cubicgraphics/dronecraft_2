package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class PCBRender {
    // only call these functions from within a gui screen

    public static void RenderPCB(MatrixStack matrix, int left, int top, PCBData PCB, ContainerScreen<?> screen){
        if(PCB != null){
            ResourceLocation TEXTURE = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_components.png");
            Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);
            //should draw wires if there are any
            if (PCB.PCBWiresArray != null){
                for (int x = 0; x < PCB.length; x++) {
                    for (int y = 0; y < PCB.width; y++) {
                        if(PCB.PCBWiresArray[x][y] != null){
                            RenderSystem.color4f(PCB.PCBWiresArray[x][y].getColor().r, PCB.PCBWiresArray[x][y].getColor().g, PCB.PCBWiresArray[x][y].getColor().b,PCB.PCBWiresArray[x][y].getColor().a);
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
                PCB.ComponentArray[i].getComponent().RenderComponent(matrix,left+(PCB.ComponentArray[i].x*8),top + (PCB.ComponentArray[i].y*8),screen);
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
        Component.RenderComponent(matrix, left, top, screen);
    }

    public static void RenderPCBTooltips(MatrixStack matrix, PCBData PCB, int MouseX, int MouseY,int left, int top, ContainerScreen<?> screen){
        if(PCB != null){
            for (int i = 0; i < PCB.ComponentArray.length; i++) {
                if (((MouseX > left + PCB.ComponentArray[i].x*8) && (MouseX < left + PCB.ComponentArray[i].x*8 +PCB.ComponentArray[i].getComponent().Length*8))
                        && ((MouseY > top + PCB.ComponentArray[i].y*8) && (MouseY < top + PCB.ComponentArray[i].y*8 +PCB.ComponentArray[i].getComponent().Width*8)))
                {
                    screen.renderTooltip(matrix, PCB.ComponentArray[i].getComponent().getName(),MouseX,MouseY);
                }
            }
        }
    }

    //TODO change code in this function for rendering a scrollable PCB also make it click and hold to pan possibly?? - this is so large pcb's that may not fit within the screen can be displayed
    public static void RenderPCBTooltips(MatrixStack matrix, PCBData PCB, int MouseX, int MouseY,int left, int top, ContainerScreen<?> screen, int TileLength, int TileWidth, int scrollX, int scrollY){

        for (int i = 0; i < PCB.ComponentArray.length; i++) {
            if (((MouseX > left + PCB.ComponentArray[i].x*8) && (MouseX < left + PCB.ComponentArray[i].x*8 +PCB.ComponentArray[i].getComponent().Length*8))
                    && ((MouseY > top + PCB.ComponentArray[i].y*8) && (MouseY < top + PCB.ComponentArray[i].y*8 +PCB.ComponentArray[i].getComponent().Width*8)))
            {
                screen.renderTooltip(matrix, PCB.ComponentArray[i].getComponent().getName(),MouseX,MouseY);
            }
        }
    }



    public static void RenderPCBComponentTooltips(MatrixStack matrix, List<PCBComponentXY> Components, int MouseX, int MouseY, ContainerScreen<?> screen){

        for (int i = 0; i < Components.size(); i++) {
            if (Components.get(i) != null && ((MouseX >= Components.get(i).x)
                    && (MouseX <= Components.get(i).x + Components.get(i).getComponent().Length))
                    && ((MouseY >= Components.get(i).y)
                    && (MouseY <= Components.get(i).y + Components.get(i).getComponent().Width))){
                screen.renderTooltip(matrix, Components.get(i).getComponent().getName(), MouseX,MouseY);

            }
        }
    }
}

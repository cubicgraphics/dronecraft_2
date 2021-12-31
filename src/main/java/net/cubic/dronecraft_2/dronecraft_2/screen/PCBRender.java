package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.ResourceLocation;

public class PCBRender {
    // only call these functions from within a gui screen


    public static void RenderPCBComponent(MatrixStack matrix, int left, int top, PCBComponent Component, ContainerScreen<?> screen){
        ResourceLocation TEXTURE = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/pcb_components.png");
        Minecraft.getInstance().getTextureManager().bindTexture(TEXTURE);

        //TODO do magic stuff to render the pcb.


        int length = Component.Length;
        int width = Component.Width;
        
        //First draw the input
        RenderSystem.color4f(1.0F, 170F/255F, 0.0F,1.0F);
        for (int i = 0; i < Component.Inputs.length; i++) {
            screen.blit(matrix,left +(Component.Inputs[i].X*8) ,top+(Component.Inputs[i].Y*8),0,8,8,8);
        }
        //Then draw outputs
        RenderSystem.color4f(1.0F, 190F/255F, 0.0F,1.0F);
        for (int i = 0; i < Component.Outputs.length; i++) {
            screen.blit(matrix,left +(Component.Outputs[i].X*8) ,top+(Component.Outputs[i].Y*8),0,8,8,8);
        }

        //Then draw the body of the PCB Component
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
}

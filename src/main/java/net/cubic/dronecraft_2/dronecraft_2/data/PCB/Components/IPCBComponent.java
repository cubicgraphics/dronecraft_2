package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IPCBComponent {

    @OnlyIn(Dist.CLIENT)
    ITextComponent getName();

    @OnlyIn(Dist.CLIENT)
    void RenderComponent(MatrixStack matrix, int left, int top, ContainerScreen<?> screen);

    String getDefaultTranslationKey();

    String getTranslationKey();
}

package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public interface IPCBComponent {

    @OnlyIn(Dist.CLIENT)
    void RenderComponent(MatrixStack matrix, int left, int top, ContainerScreen<?> screen, PCBComponentXY<?> componentXY);
    @OnlyIn(Dist.CLIENT)
    void RenderComponent(MatrixStack matrix, int left, int top, int right, int bottom, int x, int y, ContainerScreen<?> screen, PCBComponentXY<?> componentXY);

    List<?> CalculateOutput(List<?> inputs, World worldIn, BlockPos pos, Entity entity, ItemStack item, PCBComponentXY<?> Component);

    }

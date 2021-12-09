package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.Message;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.PacketHandler;
import net.cubic.dronecraft_2.dronecraft_2.container.AreaScannerContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.CapabilityScannerArea;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class AreaScannerScreen extends ContainerScreen<AreaScannerContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/area_scanner_gui.png");//TODO make image transparent - remove white bits on corners


    public AreaScannerScreen(AreaScannerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX,mouseY,partialTicks);
        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f,1f,1f,1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack,i,j,0,0,this.xSize,this.ySize);
    }


    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack ms, int mouseX, int mouseY) {
        int i = 0;
    //TODO need to fetch this and display it locally
        this.font.drawString(ms, String.valueOf(container.getScannerRange()), 8, 30, -12829636);

        /*this.font.drawString(ms, "" + (new Object() {
            public int getScannerRange(BlockPos pos) {
                AtomicInteger _retval = new AtomicInteger(0);
                World world = playerInventory.player.getEntityWorld();
                //if  (!world.isRemote()){
                    world.getCapability(CapabilityScannerArea.SCANNER_AREA, null).ifPresent(
                            capability -> _retval.set(
                                    capability
                                            .GetScanner(pos)//crashes here
                                            .Range
                            )
                    );

                //}
                return _retval.get();
            }
        }.getScannerRange(container.getBlockPos())) + "", 11, 13, -12829636);

         */
        //PacketHandler.INSTANCE.sendToServer(new Message(container.getBlockPos()));


    }

}

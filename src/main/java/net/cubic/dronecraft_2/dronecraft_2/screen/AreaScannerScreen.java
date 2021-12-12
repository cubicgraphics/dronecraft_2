package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.PacketHandler;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer.PacketToggleScannerMode;
import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.container.AreaScannerContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill.ScannerFormat;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class AreaScannerScreen extends ContainerScreen<AreaScannerContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/area_scanner_gui.png");


    public AreaScannerScreen(AreaScannerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX,mouseY,partialTicks);

        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.itemRenderer.renderItemAndEffectIntoGUI(ModBlocks.AREA_SCANNER_BLOCK.get().asItem().getDefaultInstance(),i + 80,j + 36);

        this.font.drawString(matrixStack, String.valueOf(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetScanner(container.getBlockPos()).Range), i +8, j +16, -12829636);
        this.font.drawString(matrixStack, String.valueOf(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetAreaModeString(container.getBlockPos())), i +8, j +42, -12829636);


    }


    int Progress = 0;
    int semiProgress = 0;
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f,1f,1f,1f);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack,i,j,0,0,this.xSize,this.ySize);
        if(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetScanner(container.getBlockPos()).AreaMode == 0){
        this.blit(matrixStack,i+ 58,j + 14,196,128,60,60);
        this.blit(matrixStack,i+ 88 - (Progress/2),j + 44 - (Progress/2),226 - (Progress/2),222 - (Progress/2), Progress,Progress);

        }
        else if(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetScanner(container.getBlockPos()).AreaMode == 1) {
            this.blit(matrixStack, i + 58, j + 14, 196, 0, 60, 60);
            this.blit(matrixStack,i+ 88 - (Progress/2),j + 44 - (Progress/2),226 - (Progress/2),94 - (Progress/2), Progress,Progress);

        }
        if((Progress >= 60) && (semiProgress < 20)) {
            semiProgress++;
        }
        else if(semiProgress >= 20) {
            semiProgress = 0;
            Progress = 0;
        }
        else{
            Progress++;
        }
    }


    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {


    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        super.init(minecraft, width, height);

        addButton(new Button(this.guiLeft + 6, this.guiTop + 52, 40, 18, ITextComponent.getTextComponentOrEmpty("MODE"), button -> ToggleAreaMode(container.getBlockPos())));
    }
    public void ToggleAreaMode(BlockPos blockpos){
        PacketHandler.sendToServer(new PacketToggleScannerMode(blockpos));
    }
}

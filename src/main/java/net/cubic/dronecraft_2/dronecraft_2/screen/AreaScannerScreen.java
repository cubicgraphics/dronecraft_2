package net.cubic.dronecraft_2.dronecraft_2.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.PacketHandler;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer.PacketSetScannerRange;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer.PacketToggleScannerMode;
import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.container.AreaScannerContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.widget.ExtendedButton;
import net.minecraftforge.fml.client.gui.widget.Slider;

public class AreaScannerScreen extends ContainerScreen<AreaScannerContainer> {
    private final ResourceLocation GUI = new ResourceLocation(dronecraft_2Main.MOD_ID, "textures/gui/area_scanner_gui.png");


    public AreaScannerScreen(AreaScannerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    ExtendedButton ModeButton;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack,mouseX,mouseY,partialTicks);

        this.renderHoveredTooltip(matrixStack,mouseX,mouseY);
        int i = this.guiLeft;
        int j = this.guiTop;


        this.itemRenderer.zLevel = 200.0F;
        this.itemRenderer.renderItemAndEffectIntoGUI(ModBlocks.AREA_SCANNER_BLOCK.get().asItem().getDefaultInstance(),i + 80,j + 32);
        for (int k = 0;  k < container.GetScannerPosts(); k++) { //works but they overlap wierdly
            this.itemRenderer.zLevel = this.itemRenderer.zLevel - 20.0F;
            this.itemRenderer.renderItemAndEffectIntoGUI(ModBlocks.AREA_SCANNER_POST_BLOCK.get().asItem().getDefaultInstance(),i + 80,j + 40 + (k*8));
        }
        this.itemRenderer.zLevel = 0F;

        //this.font.drawString(matrixStack, String.valueOf(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetScanner(container.getBlockPos()).Range), i +8, j +16, -12829636);
        this.font.drawString(matrixStack, /*String.valueOf(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetAreaModeString(container.getBlockPos()))*/ "Mode:", i +6, j +12, -12829636);
        ModeButton.setMessage(ITextComponent.getTextComponentOrEmpty(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetAreaModeString(container.getBlockPos())));


    }





    int Progress = 0;
    int semiProgress = 0;
    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(ModSettings.FBackgroundR(),ModSettings.FBackgroundG(),ModSettings.FBackgroundB(),ModSettings.FBackgroundA());
        this.minecraft.getTextureManager().bindTexture(GUI);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(matrixStack,i,j,0,0,this.xSize,this.ySize);

        //Foreground (still background) rendering
        RenderSystem.color4f(ModSettings.FForegroundR(),ModSettings.FForegroundG(),ModSettings.FForegroundB(),ModSettings.FForegroundA());
        if(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetScanner(container.getBlockPos()).AreaMode == 0){
            this.blit(matrixStack,i+ 58,j + 10,196,128,60,60);
            this.blit(matrixStack,i+ 88 - (Progress/2),j + 40 - (Progress/2),226 - (Progress/2),222 - (Progress/2), Progress,Progress);
        }
        else if(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetScanner(container.getBlockPos()).AreaMode == 1) {
            this.blit(matrixStack, i + 58, j + 10, 196, 0, 60, 60);
            this.blit(matrixStack,i+ 88 - (Progress/2),j + 40 - (Progress/2),226 - (Progress/2),94 - (Progress/2), Progress,Progress);

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

        ModeButton = new ExtendedButton(this.guiLeft + 4, this.guiTop + 22, 50, 16, ITextComponent.getTextComponentOrEmpty("MODE"), button -> ToggleAreaMode(container.getBlockPos()));

        Slider slide = new Slider(this.guiLeft + 4, this.guiTop + 72, 168, 12,
                ITextComponent.getTextComponentOrEmpty("Range: "),ITextComponent.getTextComponentOrEmpty(""),
                1,container.GetMaxScanningRange(),WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetScanner(container.getBlockPos()).Range,
                false,true, null/*slider ->SetScanningRange(container.getBlockPos(), ((Slider) slider).getValueInt())*/,onPress());
        addButton(ModeButton);
        addButton(slide);
        ModeButton.setMessage(ITextComponent.getTextComponentOrEmpty(WorldGlobalVar.WorldVariables.get(playerInventory.player.world).Scanners.GetAreaModeString(container.getBlockPos())));
    }


    protected final Slider.ISlider onPress(){ //TODO quite inefficient as this gets called around 5 times even if it is clicked once - bad cos it has to loop though all the scanners at least twice - But it does work
        return slider -> {
            SetScanningRange(container.getBlockPos(), slider.getValueInt());
        };
    }

    public void ToggleAreaMode(BlockPos blockpos){
        PacketHandler.sendToServer(new PacketToggleScannerMode(blockpos));
    }

    public void SetScanningRange(BlockPos blockpos, int NewRange){
        if(NewRange <= container.GetMaxScanningRange()){
            PacketHandler.sendToServer(new PacketSetScannerRange(blockpos, NewRange));
        }
    }
}

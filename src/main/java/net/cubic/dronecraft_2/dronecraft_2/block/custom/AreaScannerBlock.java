package net.cubic.dronecraft_2.dronecraft_2.block.custom;

import net.cubic.dronecraft_2.dronecraft_2.Utill.network.PacketHandler;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.packets.to_client.SyncScannerAreaCapability;
import net.cubic.dronecraft_2.dronecraft_2.container.AreaScannerContainer;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.CapabilityScannerArea;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.ScannerFormat;
import net.cubic.dronecraft_2.dronecraft_2.tileentity.AreaScannerTileEntity;
import net.cubic.dronecraft_2.dronecraft_2.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class AreaScannerBlock extends Block {

    public AreaScannerBlock(Properties properties) {
        super(properties
                .hardnessAndResistance(6f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .setRequiresTool()
        );
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (!player.isCrouching()){
                if (tileEntity instanceof AreaScannerTileEntity){
                    worldIn.getCapability(CapabilityScannerArea.SCANNER_AREA).ifPresent(h -> {
                                PacketHandler.sendToClient(new SyncScannerAreaCapability(h.GetScanners()), ((ServerPlayerEntity) player));
                                System.out.println("Should have sent packet");
                            });
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn,pos);

                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider,tileEntity.getPos());
                }
                else{
                    throw new IllegalStateException("Our container provider is missing");
                }
            }
        }

        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.dronecraft_2.area_scanner_gui");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity player) {
                return new AreaScannerContainer(i, worldIn,pos,playerInventory,player);
            }
        };
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return ModTileEntities.Area_Scanner_Tile_Entity.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
        if (!worldIn.isRemote){
            worldIn.getCapability(CapabilityScannerArea.SCANNER_AREA).ifPresent(h-> {
                h.AddScanner(pos,5,0);
                System.out.println("saving location of scanner");
                System.out.println(h.GetScanners().toString());
            });
        }
    }





    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (!worldIn.isRemote()){
            removeAreaScannerData(worldIn, pos);
        }
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);
        if (!world.isRemote()){
            removeAreaScannerData(world, pos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);
        if (!worldIn.isRemote()){
            removeAreaScannerData(worldIn, pos);
        }
    }

    public void removeAreaScannerData(World worldIn, BlockPos pos){
        if (!worldIn.isRemote){
            worldIn.getCapability(CapabilityScannerArea.SCANNER_AREA).ifPresent(h-> {
                h.RemoveScanner(pos);
                System.out.println("removed location of a scanner");
            });
        }
    }
}

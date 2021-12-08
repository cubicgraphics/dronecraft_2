package net.cubic.dronecraft_2.dronecraft_2.block.custom;

import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.CapabilityScannerArea;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class AreaScannerBlock extends Block {

    public AreaScannerBlock(Properties properties) {
        super(properties
                .hardnessAndResistance(6f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .setRequiresTool()
        );
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

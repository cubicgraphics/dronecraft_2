package net.cubic.dronecraft_2.dronecraft_2.block.custom;

import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.CapabilityScannerArea;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
        setRegistryName("area_scanner_block");
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
        if (!worldIn.isRemote){
            worldIn.getCapability(CapabilityScannerArea.SCANNER_AREA).ifPresent(h-> {
                h.AddScanner(pos,1);
                System.out.println("saving location of scanner");
                System.out.println(h.GetScannerAreas().toString());
            });
        }
    }


    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        if (canHarvestBlock(state, worldIn, pos, player)){
            removeAreaScannerData(worldIn, pos);
        }
    }

    public void removeAreaScannerData(World worldIn, BlockPos pos){
        if (!worldIn.isRemote){
            worldIn.getCapability(CapabilityScannerArea.SCANNER_AREA).ifPresent(h-> {
                h.RemoveScanner(pos);
                System.out.println("removing location of scanner");
            });
        }
    }
}

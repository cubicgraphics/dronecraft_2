package net.cubic.dronecraft_2.dronecraft_2.events;

import net.cubic.dronecraft_2.dronecraft_2.Utill.CropUtil;
import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void ScanBlock(BlockEvent.BreakEvent BlockEvent) {
        //do stuff for scanner when a block gets broken
        //BlockEvent.setCanceled(true);

    }

    public static void WithinScannerBlock(World worldIn, BlockPos Pos) {
        if (worldIn.isRemote) {
            return;
        }
        List<BlockPos> scanners = WorldGlobalVar.WorldVariables.get(worldIn).Scanners.GetScannersSurveyingBlock(worldIn, Pos);
        //System.out.println("fetched scanners in range");
        if (scanners.isEmpty()) {
            return;
        }
        for (BlockPos scanner : scanners) {
            if (worldIn.getBlockState(scanner).getBlock().getDefaultState() == ModBlocks.AREA_SCANNER_BLOCK.get().getDefaultState()) {
                ScannerAction(worldIn,Pos, scanner); //Block is within scanner, do stuff to it.
            }
            else{
                WorldGlobalVar.WorldVariables.get(worldIn).Scanners.RemoveScanner(scanner);
                WorldGlobalVar.WorldVariables.get(worldIn).syncData(worldIn);
            }
        }
    }

    public static void ScannerAction(World worldIn, BlockPos blockPos, BlockPos scannerPos){
        //ServerUtil.SendToAllPlayers("Crop grown at " + Pos.toString() + "Scanned by " + scanner);
        //Do things with scanner here like send a drone to the block or something
        //the code below is temporary, it just destroys the block, drops the blocks items and then places the block back again

        BlockState block = worldIn.getBlockState(blockPos);
        if(block.getBlock() instanceof AttachedStemBlock){
            if(worldIn.getBlockState(blockPos.offset(block.get(HorizontalFaceBlock.HORIZONTAL_FACING))).getBlock() instanceof StemGrownBlock){
                blockPos = blockPos.offset(block.get(HorizontalFaceBlock.HORIZONTAL_FACING));
            }
        }
        worldIn.destroyBlock(blockPos,true); // only here becasue i have not added drones yet
        if(block.getBlock() instanceof CropsBlock){
            worldIn.setBlockState(blockPos,block.getBlock().getDefaultState()); //places a default crop block back
        }
    }

    @SubscribeEvent
    public static void OnCropGrown(BlockEvent.CropGrowEvent.Post CropGrowEvent) {  //TODO sugar cane does not get called when it grows

        if (CropUtil.IsFullyGrown((World) CropGrowEvent.getWorld(), CropGrowEvent.getState(), CropGrowEvent.getPos())) {
            WithinScannerBlock( (World) CropGrowEvent.getWorld(),CropGrowEvent.getPos() );
        }
    }

    @SubscribeEvent
    public static void OnBoneMeal(BonemealEvent event) {
        if((event.getBlock().isIn(BlockTags.CROPS) || (event.getBlock().getBlock() instanceof IGrowable))){
            if (CropUtil.IsFullyGrown(event.getWorld(), event.getBlock(), event.getPos())) {
                WithinScannerBlock(event.getWorld(),event.getPos() );
            }
        }
    }
}

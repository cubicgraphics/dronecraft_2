package net.cubic.dronecraft_2.dronecraft_2.events;


import net.cubic.dronecraft_2.dronecraft_2.Utill.CropUtil;
import net.cubic.dronecraft_2.dronecraft_2.Utill.ServerUtil;
import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill.ScannerFormat;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.block.*;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;


@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void ScanBlock(BlockEvent.BreakEvent BlockEvent) {
        String name = BlockEvent.getPlayer().getDisplayName().toString();
        if (name.equals( "noicdeppilf")) { //friend requested this
            Random rand = new Random();
            if (rand.nextInt(100) == 5) { //he said the number 5
                BlockEvent.setCanceled(true);
                BlockEvent.getPlayer().sendMessage(ITextComponent.getTextComponentOrEmpty("HA"), BlockEvent.getPlayer().getUniqueID()); // might work else may cause a crash IDK
            }
        }

        System.out.println("blockupdt-" + BlockEvent.getPos().toString());
    }

    public static void WithinScannerBlock(World worldIn, BlockPos Pos) {
        if (!worldIn.isRemote) {
            List<BlockPos> scanners = WorldGlobalVar.WorldVariables.get(worldIn).Scanners.GetScannersSurveyingBlock(worldIn, Pos);
            System.out.println("fetched scanners in range");
            if (!scanners.isEmpty()) {
                for (BlockPos scanner : scanners) {
                    if (worldIn.getBlockState(scanner).getBlock().getDefaultState() == ModBlocks.AREA_SCANNER_BLOCK.get().getDefaultState()) {
                        ServerUtil.SendToAllPlayers("Crop grown at " + Pos.toString() + "Scanned by " + scanner);
                        //Do things with scanner here like send a drone to the block or something
                        BlockState block = worldIn.getBlockState(Pos);
                        if(block.getBlock() instanceof AttachedStemBlock){
                           if(worldIn.getBlockState(Pos.offset(block.get(HorizontalFaceBlock.HORIZONTAL_FACING))).getBlock() instanceof StemGrownBlock){
                                Pos = Pos.offset(block.get(HorizontalFaceBlock.HORIZONTAL_FACING));
                           }
                        }
                        worldIn.destroyBlock(Pos,true); // only here becasue it is
                        if(block.getBlock() instanceof CropsBlock){
                           worldIn.setBlockState(Pos,block.getBlock().getDefaultState());
                        }
                    }
                    else{
                        WorldGlobalVar.WorldVariables.get(worldIn).Scanners.RemoveScanner(scanner);
                        WorldGlobalVar.WorldVariables.get(worldIn).syncData(worldIn);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void OnCropGrown(BlockEvent.CropGrowEvent.Post CropGrowEvent) {

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

    /*@SubscribeEvent
    public static void BlockUpdateEvent(BlockEvent event){
        if (CropUtil.IsFullyGrown((World) event.getWorld(), event.getState(), event.getPos())) {
            WithinScannerBlock( (World) event.getWorld(),event.getPos() );
        }
    }

     */


}

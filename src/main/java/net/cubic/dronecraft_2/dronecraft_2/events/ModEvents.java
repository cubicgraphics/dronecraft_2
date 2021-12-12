package net.cubic.dronecraft_2.dronecraft_2.events;


import net.cubic.dronecraft_2.dronecraft_2.Utill.CropUtil;
import net.cubic.dronecraft_2.dronecraft_2.Utill.ServerUtil;
import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill.ScannerFormat;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
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
            List<ScannerFormat> scanners = WorldGlobalVar.WorldVariables.get(worldIn).Scanners.GetScannersSurveyingBlock(Pos);
            System.out.println("fetched scanners in range");
            if (!scanners.isEmpty()) {
                for (ScannerFormat scanner : scanners) {
                    if (worldIn.getBlockState(scanner.ScannerPos).getBlock().getDefaultState() == ModBlocks.AREA_SCANNER_BLOCK.get().getDefaultState()) {
                        ServerUtil.SendToAllPlayers("Crop grown at " + Pos.toString() + "Scanned by " + scanner.ScannerPos);
                        //Do things with scanner here like send a drone to the block or something
                        worldIn.destroyBlock(Pos,true);

                    }
                    else{
                        WorldGlobalVar.WorldVariables.get(worldIn).Scanners.RemoveScanner(scanner.ScannerPos);
                        WorldGlobalVar.WorldVariables.get(worldIn).syncData(worldIn);
                    }
                }
            }
        }
    }



    @SubscribeEvent
    public static void OnCropGrown(BlockEvent.CropGrowEvent.Post CropGrowEvent) {

        if (CropUtil.IsFullyGrown(CropGrowEvent.getState())) {
            WithinScannerBlock( (World) CropGrowEvent.getWorld(),CropGrowEvent.getPos() );
        }
    }

    @SubscribeEvent
    public static void OnBoneMeal(BonemealEvent event) { //fires before the block grows - will need to find a way to do this after the crop has grown
        if (CropUtil.IsFullyGrown(event.getBlock())) {
            WithinScannerBlock(event.getWorld(),event.getPos() );

        }
    }


}

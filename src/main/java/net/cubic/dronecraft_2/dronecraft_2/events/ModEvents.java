package net.cubic.dronecraft_2.dronecraft_2.events;


import net.cubic.dronecraft_2.dronecraft_2.Utill.CropUtil;
import net.cubic.dronecraft_2.dronecraft_2.Utill.ServerUtil;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.CapabilityScannerArea;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.ScannerFormat;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.FireworkParticle;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.particles.IParticleData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Random;


@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void ScanBlock(BlockEvent.BreakEvent BlockEvent) {
        String name = BlockEvent.getPlayer().getDisplayName().toString();
        if (name == "noicdeppilf") { //friend requested this
            Random rand = new Random();
            if (rand.nextInt(100) == 5) { //he said the number 5
                BlockEvent.setCanceled(true);
                BlockEvent.getPlayer().sendMessage(ITextComponent.getTextComponentOrEmpty("HA"), BlockEvent.getPlayer().getUniqueID()); // might work else may cause a crash idk
            }
        }

        System.out.println("blockupdt-" + BlockEvent.getPos().toString());
    }

    public static void WithinScannerBlock(World worldIn, BlockPos Pos) {
        if (!worldIn.isRemote) {
            worldIn.getCapability(CapabilityScannerArea.SCANNER_AREA).ifPresent(h -> {
                ScannerFormat scan = h.GetClosestScanner(Pos);
                System.out.println("fetched closest scanner");
                if (scan != null) {
                    System.out.println("closest scanner was " + scan);
                    if (h.IsInRange(Pos, scan)) {
                        System.out.println("Was within the range of the scanner");
                        ServerUtil.SendToAllPlayers("Crop grown at " + Pos.toString());
                    }
                }
            });
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
            WithinScannerBlock( (World) event.getWorld(),event.getPos() );

        }
    }


}

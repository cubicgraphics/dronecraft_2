package net.cubic.dronecraft_2.dronecraft_2.events;


import net.cubic.dronecraft_2.dronecraft_2.Utill.CropUtil;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.entity.ai.brain.task.BoneMealCropsTask;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;


@Mod.EventBusSubscriber(modid = dronecraft_2Main.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void ScanBlock(BlockEvent.BreakEvent  BlockEvent){
        String name = BlockEvent.getPlayer().getDisplayName().toString();
        if (name == "noicdeppilf"){ //friend requested this
            Random rand = new Random();
            if (rand.nextInt(100) == 5){
                BlockEvent.setCanceled(true);
                BlockEvent.getPlayer().sendMessage(ITextComponent.getTextComponentOrEmpty("HA"),BlockEvent.getPlayer().getUniqueID()); // might work else may cause a crash idk
            }
        }

        System.out.println("blockupdt-" + BlockEvent.getPos().toString());
    }

    @SubscribeEvent
    public static void OnCropGrown(BlockEvent.CropGrowEvent.Post CropGrowEvent){

        if (CropUtil.IsFullyGrown(CropGrowEvent.getState())){
            System.out.println("CROP GROWN HERE AAA");

        }
    }

    @SubscribeEvent
    public static void OnBoneMeal(BonemealEvent event){
        if (CropUtil.IsFullyGrown(event.getBlock())) {
            System.out.println("CROP GROWN HERE AAA BONEMEAL");

        }
    }
}

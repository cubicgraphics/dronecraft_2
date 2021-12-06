package net.cubic.dronecraft_2.dronecraft_2.Utill;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.event.world.BlockEvent;
import sun.security.util.Debug;

public class CropUtil {

    public static boolean IsFullyGrown(BlockState blockState){
        boolean IsGrown = false;
        if (blockState.getBlock().isIn(BlockTags.CROPS)){
            //System.out.println("Is in the crop blocktag group");
            try {
                CropsBlock crop = (CropsBlock) blockState.getBlock();
                if (crop.isMaxAge(blockState)) {
                    IsGrown = true;
                }
            } catch (Exception e) {
                Debug debug = new Debug();
                debug.println("Dronecraft 2 does not like the fact you put a block that doesn't extend CropBlocks into the crop tag, the offending block is: " + blockState.getBlock());
                System.out.println("Dronecraft 2 does not like the fact you put a block that doesn't extend CropBlocks into the crop tag, the offending block is: " + blockState.getBlock());
                e.printStackTrace();
            }
        }
        return IsGrown;
    }
}

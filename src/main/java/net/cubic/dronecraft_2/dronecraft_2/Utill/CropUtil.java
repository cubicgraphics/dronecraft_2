package net.cubic.dronecraft_2.dronecraft_2.Utill;

import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CropUtil {
    public static boolean IsFullyGrown(World world, BlockState blockState, BlockPos pos){
        boolean IsGrown = false;
        if (blockState.getBlock().isIn(BlockTags.CROPS)){
            if(world.getBlockState(pos).getBlock() instanceof StemGrownBlock){
                IsGrown = true;
            }
            try {
                if (((CropsBlock) blockState.getBlock()).isMaxAge(blockState)) {
                    IsGrown = true;
                }
            } catch (Exception e) {
                System.out.println("Dronecraft 2 does not like the fact you put a block that doesn't extend CropBlocks into the crop tag, the offending block is: " + blockState.getBlock());
                e.printStackTrace();
            }
        }
        else {
            if(world.getBlockState(pos.add(0,-1,0)).getBlock().getDefaultState() == blockState.getBlock().getDefaultState()){//this should cover cactus, bamboo //TODO sugar cane does not get called when it grows, need to use onCropsGrowPost() when a sugar cane grows - somehow overide sugarcane class
                IsGrown = true;
            }
            else if((world.getBlockState(pos).getBlock() instanceof StemGrownBlock ) || (world.getBlockState(pos).getBlock() instanceof AttachedStemBlock) ){
                IsGrown = true;
            }
        }
        return IsGrown;
    }
}


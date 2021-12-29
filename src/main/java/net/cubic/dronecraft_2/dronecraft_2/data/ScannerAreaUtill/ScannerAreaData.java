package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill;

import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScannerAreaData {

    HashMap<BlockPos,ScannerFormat> Scanners;

     //WorldGlobalVar.WorldVariables.get(world).Scanners.GetScanners() //to retrieve scanners variables

     //WorldGlobalVar.WorldVariables.get(world).Scanners.SetScanners(new ArrayList<>());
     //WorldGlobalVar.WorldVariables.get(world).syncData(world);// To write to world data


    public ScannerAreaData(){
        Scanners = new HashMap<>();
    }


    public void AddScanner(BlockPos blockPos, int radius, int areaMode) {
        this.Scanners.put(blockPos,new ScannerFormat(radius,areaMode));
    }

    public void SetScanners(HashMap<BlockPos,ScannerFormat> scannerlist) {
        this.Scanners = scannerlist;
    }

    public void SetScannerRange(BlockPos blockPos, int range) {
        SetScanner(blockPos,range,GetAreaMode(blockPos));
    }
    public void SetAreaMode(BlockPos blockPos, int AreaMode) {
        SetScanner(blockPos,GetRange(blockPos),AreaMode);
    }
    public void SetScanner(BlockPos blockPos, int range, int AreaMode) {
        Scanners.replace(blockPos,new ScannerFormat(range,AreaMode));
    }

    public String GetAreaModeString(BlockPos blockpos){
        int AreaMode = this.GetScanner(blockpos).AreaMode;
        if(AreaMode == 0){
            return "sphere";
        }
        else if(AreaMode == 1){
            return "square";
        }
        else {
            return "AreaMode INVALID";
        }
    }

    public int GetAreaMode(BlockPos blockpos){
        return this.GetScanner(blockpos).AreaMode;
    }
    public int GetRange(BlockPos blockpos){
        return this.GetScanner(blockpos).Range;
    }

    public void RemoveScanner(BlockPos blockPos) {
        Scanners.remove(blockPos);
    }


    public Boolean IsInRange(World worldIn, BlockPos blockpos, BlockPos scannerPos) {
        if (GetAreaMode(scannerPos) == 0) {
            return blockpos.withinDistance(scannerPos, GetRange(scannerPos) + 1);  //checks in a circle around the scanner of its set radius
        }
        else if(GetAreaMode(scannerPos) == 1){ //checks in a square around the base of the scanner (will have to add a new var to the capability when I add the scanner pole)
            boolean search = true;
            int PostCount = 0;
            while (search){
                if(worldIn.getBlockState(scannerPos.add(0,-1 - PostCount,0)).getBlock().getDefaultState() == ModBlocks.AREA_SCANNER_POST_BLOCK.get().getDefaultState()){
                    PostCount = PostCount + 1;
                }
                else{
                    search = false;
                }
            }
            return ((Math.abs(blockpos.getX() - scannerPos.getX()) <= GetRange(scannerPos)) && (Math.abs(blockpos.getZ() - scannerPos.getZ()) <= GetRange(scannerPos)) && ((blockpos.getY() <= scannerPos.getY()) && (scannerPos.getY() - blockpos.getY() <= PostCount)));
        }
        else {
            return Boolean.FALSE;
        }
    }

    public List<BlockPos> GetScannersSurveyingBlock(World worldIn, BlockPos blockPos) { // returns a lists of blockPos of scanner blockPos

        List<BlockPos> scannerBlockList = new ArrayList<>();
        for (BlockPos i : Scanners.keySet()) {
            if (IsInRange(worldIn,blockPos,i)){
                scannerBlockList.add(i);
            }
        }
        return scannerBlockList;
    }

        //returns null if not found
    public ScannerFormat GetScanner(BlockPos blockPos) {
        return Scanners.get(blockPos);
    }

    public HashMap<BlockPos, ScannerFormat> GetScanners() {
        return this.Scanners;
    }


}

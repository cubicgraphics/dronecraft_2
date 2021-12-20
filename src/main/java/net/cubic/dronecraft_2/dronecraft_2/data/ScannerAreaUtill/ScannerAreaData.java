package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill;

import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ScannerAreaData {

     List<ScannerFormat> Scanners;

     //WorldGlobalVar.WorldVariables.get(world).Scanners.GetScanners() //to retrieve scanners variables

     //WorldGlobalVar.WorldVariables.get(world).Scanners.SetScanners(new ArrayList<>());
     //WorldGlobalVar.WorldVariables.get(world).syncData(world);// To write to world data


    public ScannerAreaData(){
        Scanners = new ArrayList<>();
    }


    public void AddScanner(BlockPos blockPos, int radius, int areaMode) {
        this.Scanners.add(new ScannerFormat(blockPos, radius,areaMode)); // this now works because I made the default value a new arraylist
    }

    public void SetScanners(List<ScannerFormat> scannerlist) {
        this.Scanners = scannerlist;
    }

    public void SetScannerRange(BlockPos blockpos, int range) {
        for (int i = 0; i < Scanners.size(); i++) {
            if (Scanners.get(i).ScannerPos.equals(blockpos)){
                Scanners.set(i,new ScannerFormat(blockpos,range,Scanners.get(i).AreaMode));
                System.out.println("scanner found and new rnge set");
            }
        }
    }

    public void SetScanner(BlockPos blockPos, int range, int AreaMode) {
        for (int i = 0; i < Scanners.size(); i++) {
            if (Scanners.get(i).ScannerPos.equals(blockPos)){
                Scanners.set(i,new ScannerFormat(blockPos,range,AreaMode));
            }
        }
    }

    public void SetAreaMode(BlockPos blockpos, int AreaMode) {
        for (int i = 0; i < Scanners.size(); i++) {
            if (Scanners.get(i).ScannerPos.equals(blockpos)){
                Scanners.set(i,new ScannerFormat(blockpos,Scanners.get(i).Range,AreaMode));
            }
        }
    }
    public String GetAreaModeString(BlockPos blockpos){
        int areamode = this.GetScanner(blockpos).AreaMode;
        if(areamode == 0){
            return "sphere";
        }
        else if(areamode == 1){
            return "square";
        }
        else {
            return "AreaMode INVALID";
        }
    }
    public int GetAreaMode(BlockPos blockpos){
        return this.GetScanner(blockpos).AreaMode;
    }

    public void RemoveScanner(BlockPos blockpos) {
        for (int i = 0; i < Scanners.size(); i++) {
            if (Scanners.get(i).ScannerPos.equals(blockpos)){
                Scanners.remove(i);
            }
        }
    }


    public Boolean IsInRange(World worldIn, BlockPos blockpos, ScannerFormat scanner) {
        if (scanner.AreaMode == 0) {
            return blockpos.withinDistance(scanner.ScannerPos, scanner.Range + 1);  //checks in a circle around the scanner of its set radius
        }
        else if(scanner.AreaMode == 1){ //checks in a square around the base of the scanner (will have to add a new var to the capability when I add the scanner pole)
            boolean search = true;
            int PostCount = 0;
            while (search){
                if(worldIn.getBlockState(scanner.ScannerPos.add(0,-1 - PostCount,0)).getBlock().getDefaultState() == ModBlocks.AREA_SCANNER_POST_BLOCK.get().getDefaultState()){
                    PostCount = PostCount + 1;
                }
                else{
                    search = false;
                }
            }
            return ((Math.abs(blockpos.getX() - scanner.ScannerPos.getX()) <= scanner.Range) && (Math.abs(blockpos.getZ() - scanner.ScannerPos.getZ()) <= scanner.Range) && ((blockpos.getY() <= scanner.ScannerPos.getY()) && (scanner.ScannerPos.getY() - blockpos.getY() <= PostCount)));
        }
        else {
            return Boolean.FALSE;
        }
    }

    public List<ScannerFormat> GetScannersSurveyingBlock(World worldIn, BlockPos blockpos) { // returns a lists of scanner blocks
        List<ScannerFormat> scannerList = new ArrayList<>();
        for (ScannerFormat scannerFormat : Scanners) {
            if (IsInRange(worldIn,blockpos, scannerFormat)) {
                scannerList.add(scannerFormat);
            }
        }
        return scannerList;
    }

        //returns null if not found
    public ScannerFormat GetScanner(BlockPos blockpos) {
        ScannerFormat found = new ScannerFormat();
        for (ScannerFormat scanner : Scanners) {
            if (scanner.ScannerPos.equals(blockpos)) {
                found =  scanner;
            }
        }
        return found;
    }

    public List<ScannerFormat> GetScanners() {
        return this.Scanners;
    }


}

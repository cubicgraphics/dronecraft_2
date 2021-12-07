package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability;

import net.minecraft.dispenser.IPosition;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class DefaultScannerArea implements IScannerArea {

     List<ScannerFormat> Scanners;

    public DefaultScannerArea(){
        Scanners = new ArrayList<>();
    }


    @Override
    public void AddScanner(BlockPos blockPos, int radius) {
        this.Scanners.add(new ScannerFormat(blockPos, radius)); // this now works because I made the default value a new arraylist
    }

    @Override
    public void SetScanners(List<ScannerFormat> scannerlist) {
        this.Scanners = scannerlist;
    }

    @Override
    public void RemoveScanner(BlockPos blockpos) {
        for (int i = 0; i < Scanners.size(); i++) {
            if (Scanners.get(i).ScannerPos.equals(blockpos)){
                Scanners.remove(i);
            }
        }
    }

    @Override
    public Boolean IsInRange(BlockPos blockpos, ScannerFormat scannerPos) {
        return blockpos.withinDistance(scannerPos.ScannerPos, scannerPos.Range);  //checks in a circle around the scanner of its set radius
    }

    @Override
    public ScannerFormat GetClosestScanner(BlockPos blockpos) {
        ScannerFormat scanner = null;
        double finalDist = 10000000.0;
        for (int i = 0; i < Scanners.size(); i++) {
            double dist = blockpos.distanceSq(Scanners.get(i).ScannerPos);
            if (dist < finalDist){
                finalDist = dist;
                scanner = Scanners.get(i);
            }
        }
        return scanner;
    }

    @Override //returns null if not found
    public ScannerFormat GetScanner(BlockPos blockpos) {
        ScannerFormat found = null;
        for (int i = 0; i < Scanners.size(); i++) {
            if (Scanners.get(i).ScannerPos == blockpos){
                found = Scanners.get(i);
            }
        }
        return found;
    }

    @Override
    public List<ScannerFormat> GetScanners() {
        return this.Scanners;
    }


}

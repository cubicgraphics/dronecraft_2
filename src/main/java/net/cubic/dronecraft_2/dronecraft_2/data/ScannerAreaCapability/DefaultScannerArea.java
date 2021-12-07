package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class DefaultScannerArea implements IScannerArea {

     List<ScannerFormat> Scanners;

    public DefaultScannerArea(){
        Scanners = new ArrayList<>();
    }


    @Override
    public void AddScanner(BlockPos blockPos, int radius, int areaMode) {
        this.Scanners.add(new ScannerFormat(blockPos, radius,areaMode)); // this now works because I made the default value a new arraylist
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
    public Boolean IsInRange(BlockPos blockpos, ScannerFormat scanner) {
        if (scanner.AreaMode == 0) {
            return blockpos.withinDistance(scanner.ScannerPos, scanner.Range + 1);  //checks in a circle around the scanner of its set radius
        }
        else if(scanner.AreaMode == 1){ //checks in a square around the base of the scanner (will have to add a new var to the capability when I add the scanner pole)
            return ((Math.abs(blockpos.getX() - scanner.ScannerPos.getX()) <= scanner.Range) && (Math.abs(blockpos.getZ() - scanner.ScannerPos.getZ()) <= scanner.Range) && blockpos.getY() == scanner.ScannerPos.getY());
        }
        else {
            return Boolean.FALSE;
        }
    }

    @Override
    public ScannerFormat GetClosestScanner(BlockPos blockpos) {
        ScannerFormat scanner = null;
        double finalDist = 10000000.0;
        for (ScannerFormat scannerFormat : Scanners) {
            double dist = blockpos.distanceSq(scannerFormat.ScannerPos);
            if (dist < finalDist) {
                finalDist = dist;
                scanner = scannerFormat;
            }
        }
        return scanner;
    }

    @Override //returns null if not found
    public ScannerFormat GetScanner(BlockPos blockpos) {
        ScannerFormat found = null;
        for (ScannerFormat scanner : Scanners) {
            if (scanner.ScannerPos == blockpos) {
                found = scanner;
            }
        }
        return found;
    }

    @Override
    public List<ScannerFormat> GetScanners() {
        return this.Scanners;
    }


}

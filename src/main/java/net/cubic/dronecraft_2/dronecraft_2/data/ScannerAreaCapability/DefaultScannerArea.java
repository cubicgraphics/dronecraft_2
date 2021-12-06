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
    public void AddScanner(BlockPos blockPos, int radius) {
        this.Scanners.add(new ScannerFormat(blockPos, radius)); // this now works because I made the default value a new arraylist
    }

    @Override
    public void SetScanners(List<ScannerFormat> scannerlist) {
        this.Scanners = scannerlist;
    }

    @Override
    public void RemoveScanner(BlockPos blockpos) {
        for (int i = 0; i < Scanners.size()-1; i++) {
            if (Scanners.get(i).ScannerPos == blockpos){
                Scanners.remove(i);
            }
        }
    }

    @Override
    public Boolean IsInRange(BlockPos blockpos, BlockPos scannerpos) {
        //logic here to check whether the input blockpos is within the radius of the Scannerpos
        return Boolean.FALSE;//change this later
    }

    @Override
    public BlockPos GetClosestScanner(BlockPos blockpos) {
        //logic here to return the blockpos of the closest scanner
        return blockpos;
    }

    @Override //returns null if not found
    public ScannerFormat GetScanner(BlockPos blockpos) {
        ScannerFormat found = null;
        for (int i = 0; i < Scanners.size()-1; i++) {
            if (Scanners.get(i).ScannerPos == blockpos){
                found = Scanners.get(i);
            }
        }
        return found;
    }

    @Override
    public List<ScannerFormat> GetScannerAreas() {
        return this.Scanners;
    }


}

package net.cubic.dronecraft_2.dronecraft_2.data;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public class DefaultScannerArea implements IScannerArea{

    private List<ScannerFormat> Scanners;

    public DefaultScannerArea(){
        //this.Scanners.set(0,new ScannerFormat()); // dont know if this helps probably not needed
        Scanners = null;
    }


    @Override
    public void AddScanner(BlockPos blockPos, int radius) {
        this.Scanners.add(new ScannerFormat(blockPos, radius));
    }

    @Override
    public void SetScanners(List<ScannerFormat> scannerlist) {
        this.Scanners = scannerlist;
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

    @Override
    public List<ScannerFormat> GetScannerAreas() {
        return this.Scanners;
    }


}

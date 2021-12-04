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
        /*
        List<ScannerFormat> temp;
        ScannerFormat form = new ScannerFormat(blockPos, radius);
        if (this.Scanners != null){
            System.out.println("Not first time writing to list, there should already be something in it");
            temp = this.Scanners;
            temp.add(form);

        }
        else{
            System.out.println("First time writing to scannerlist because list is null so it is set as not null with the data");
            temp = new ArrayList<>();
            temp.add(form);
        }
        this.SetScanners(temp);
        */
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

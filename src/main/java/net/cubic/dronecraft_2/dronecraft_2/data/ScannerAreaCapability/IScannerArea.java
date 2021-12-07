package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IScannerArea {

        void AddScanner(BlockPos blockPos, int radius); //Adds the location of a scanner and its range
        void SetScanners(List<ScannerFormat> scannerList);// sets the scanner list
        void RemoveScanner(BlockPos blockpos);
        Boolean IsInRange(BlockPos blockpos, ScannerFormat scannerPos); // checks whether the input block is within the range of the input scanner block
        ScannerFormat GetClosestScanner(BlockPos blockpos);
        ScannerFormat GetScanner(BlockPos blockpos);
        List<ScannerFormat> GetScanners(); // gets the list of scanners

}

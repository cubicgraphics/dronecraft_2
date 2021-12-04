package net.cubic.dronecraft_2.dronecraft_2.data;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IScannerArea {

        void AddScanner(BlockPos blockPos, int radius); //Adds the location of a scanner and its range
        void SetScanners(List<ScannerFormat> scannerList);// sets the scanner list
        Boolean IsInRange(BlockPos blockpos, BlockPos scannerpos); // checks whether the input block is within the range of the input scanner block
        BlockPos GetClosestScanner(BlockPos blockpos);
        List<ScannerFormat> GetScannerAreas(); // gets the list of scanners

}

package net.cubic.dronecraft_2.dronecraft_2.data;

import net.minecraft.util.math.BlockPos;

public class ScannerFormat {

    BlockPos ScannerPos;
    int Range;

    public ScannerFormat(){
        ScannerPos = null;
        Range = 0;
    }
    public ScannerFormat(BlockPos scannerPos, int range) {
        ScannerPos = scannerPos;
        Range = range;
    }
}

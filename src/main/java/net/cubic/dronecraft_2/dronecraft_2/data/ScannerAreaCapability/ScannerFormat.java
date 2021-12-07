package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability;

import net.minecraft.util.math.BlockPos;

public class ScannerFormat {

    public BlockPos ScannerPos;
    public int Range;
    public int AreaMode;

    public ScannerFormat(){
        ScannerPos = null;
        Range = 0;
        AreaMode = 0;//0 is circle, 1 is square area around base of scanner
    }
    public ScannerFormat(BlockPos scannerPos, int range, int areaMode) {
        ScannerPos = scannerPos;
        Range = range;
        AreaMode = areaMode;
    }
}

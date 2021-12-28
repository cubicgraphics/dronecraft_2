package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill;


public class ScannerFormat {

    public int Range;
    public int AreaMode;

    public ScannerFormat(){
        Range = 0;
        AreaMode = 0;//0 is circle, 1 is square area around base of scanner
    }

    public ScannerFormat(int range, int areaMode) {
        Range = range;
        AreaMode = areaMode;
    }
}

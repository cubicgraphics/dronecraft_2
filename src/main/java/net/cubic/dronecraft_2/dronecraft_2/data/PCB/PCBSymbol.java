package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

public class PCBSymbol {
    public float X;
    public float Y;
    public int OffsetX;
    public int OffsetY;
    public int SizeX;
    public int SizeY;

    public PCBSymbol(float x, float y, int Offsetx,int Offsety){
        X = x;
        Y = y;
        OffsetX = Offsetx;
        OffsetY = Offsety;
        SizeX = 8;
        SizeY = 8;
    }

    public PCBSymbol(float x, float y, int Offsetx,int Offsety,int sizeX, int sizeY){
        X = x;
        Y = y;
        OffsetX = Offsetx;
        OffsetY = Offsety;
        SizeX = sizeX;
        SizeY = sizeY;
    }
}

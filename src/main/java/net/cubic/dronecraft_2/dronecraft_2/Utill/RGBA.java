package net.cubic.dronecraft_2.dronecraft_2.Utill;

public class RGBA {
    public float r;
    public float g;
    public float b;
    public float a;

    public RGBA(){
        a = 1.0F;
        b = 1.0F;
        g = 1.0F;
        r = 1.0F;
    }
    public RGBA(int R, int G, int B, int A){
        a = (float)A/255;
        b = (float)B/255;
        g = (float)G/255;
        r = (float)R/255;
    }
    public RGBA(float R, float G, float B, float A){
        a = A;
        b = B;
        g = G;
        r = R;
    }
    public RGBA(int R, int G, int B){
        b = (float)B/255;
        g = (float)G/255;
        r = (float)R/255;
        a = 1.0F;
    }
    public RGBA(float R, float G, float B){
        b = B;
        g = G;
        r = R;
        a = 1.0F;
    }
}

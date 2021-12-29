package net.cubic.dronecraft_2.dronecraft_2;

public class ModSettings {
    public float[] BackgroundGUIColourRGBA;
    public float[] ForegroundGUIColourRGBA;

    public ModSettings(){
        load();
    }
    public void SetBackgroundValue(int I, float value){
        BackgroundGUIColourRGBA[I] = value;
    }
    public void SetForegroundValue(int I, float value){
        ForegroundGUIColourRGBA[I] = value;
    }
    public float GetBackgroundValue(int I){
        return BackgroundGUIColourRGBA[I];
    }
    public float GetForegroundValue(int I){
        return ForegroundGUIColourRGBA[I];
    }
    public void save(){
        System.out.println("SAVING CONFIG HERE"); //TODO save the color config to a config file
    }
    public void load(){
        BackgroundGUIColourRGBA = new float[]{1f, 1f, 1f, 1f};
        ForegroundGUIColourRGBA = new float[]{1f, 1f, 1f, 1f};
    }
}

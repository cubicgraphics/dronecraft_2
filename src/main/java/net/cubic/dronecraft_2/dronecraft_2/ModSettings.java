package net.cubic.dronecraft_2.dronecraft_2;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModSettings {

    public static final ForgeConfigSpec GENERAL_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    public static ForgeConfigSpec.IntValue BackgroundR;
    public static ForgeConfigSpec.IntValue BackgroundG;
    public static ForgeConfigSpec.IntValue BackgroundB;
    public static ForgeConfigSpec.IntValue BackgroundA;

    public static ForgeConfigSpec.IntValue ForegroundR;
    public static ForgeConfigSpec.IntValue ForegroundG;
    public static ForgeConfigSpec.IntValue ForegroundB;
    public static ForgeConfigSpec.IntValue ForegroundA;

    public static ForgeConfigSpec.BooleanValue GuiRescaling;


    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Dronecraft 2 client config");
        builder.push("GUI Color options");

        BackgroundR = builder.defineInRange("Background Red", 255, 0, 255);
        BackgroundG = builder.defineInRange("Background Green", 255, 0, 255);
        BackgroundB = builder.defineInRange("Background Blue", 255, 0, 255);
        BackgroundA = builder.defineInRange("Background Alpha", 255, 0, 255);

        ForegroundR = builder.defineInRange("Foreground Red", 255, 0, 255);
        ForegroundG = builder.defineInRange("Foreground Green", 255, 0, 255);
        ForegroundB = builder.defineInRange("Foreground Blue", 255, 0, 255);
        ForegroundA = builder.defineInRange("Foreground Alpha", 255, 0, 255);
        builder.pop();
        builder.push("GUI Rescaling");
        builder.comment("This config enables/disables the ability for certain gui's like the PCB crafter gui the ability to change the gui scale when it is in focus");
        GuiRescaling = builder.define("Gui Rescaling",true);
    }

    public static float  FBackgroundR(){
        return BackgroundR.get().floatValue() / 255;
    }
    public static float  FBackgroundG(){
        return BackgroundG.get().floatValue() / 255;
    }
    public static float  FBackgroundB(){
        return BackgroundB.get().floatValue() / 255;
    }
    public static float  FBackgroundA(){
        return BackgroundA.get().floatValue() / 255;
    }

    public static float  FForegroundR(){
        return ForegroundR.get().floatValue() / 255;
    }
    public static float  FForegroundG(){
        return ForegroundG.get().floatValue() / 255;
    }
    public static float  FForegroundB(){
        return ForegroundB.get().floatValue() / 255;
    }
    public static float  FForegroundA(){
        return ForegroundA.get().floatValue() / 255;
    }
}

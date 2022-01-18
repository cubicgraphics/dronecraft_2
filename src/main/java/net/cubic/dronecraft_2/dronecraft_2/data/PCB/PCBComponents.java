package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.DefaultPCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.SmallAddComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;


@ObjectHolder(dronecraft_2Main.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PCBComponents {
/*
    public final static PCB_IO[] simpleIn = {
            new PCB_IO(0,0,PCBVarTypes.NUMBER),
            new PCB_IO(2,0,PCBVarTypes.NUMBER)
    };
    public final static PCB_IO[] simpleOut = {
            new PCB_IO(1,1,PCBVarTypes.NUMBER)
    };

    public final static PCB_IO[] simpleDroneIn = {
            new PCB_IO(0,2,PCBVarTypes.XYZ),
            new PCB_IO(0,5,PCBVarTypes.XYZ)
    };
    public final static PCB_IO[] simpleDroneOut = {
            new PCB_IO(7,2,PCBVarTypes.XYZ),
    };
 */
    public static DefaultPCBComponent SMALL_ADD;
    public static DefaultPCBComponent SMALL_SUB;
    public static DefaultPCBComponent SMALL_MULTIPLY;
    public static DefaultPCBComponent SMALL_DIVIDE;
    public static DefaultPCBComponent SMALL_EQUALS;
    public static DefaultPCBComponent SIMPLE_DRONE_BRAIN;





    @SubscribeEvent
    public static void onPCBRegistry(final RegistryEvent.Register<DefaultPCBComponent> event) {
        PCB_IO[] simpleIn = {
                new PCB_IO(0,0,new VarType(new RGBA(255,255,0),Float.class).setRegistryName("number")), //TODO somehow fetch this from the PCBVarTypes file instead of making new ones here, kind of defeats the point of making it a registry
                new PCB_IO(2,0,new VarType(new RGBA(255,255,0),Float.class).setRegistryName("number"))
        };
        PCB_IO[] simpleOut = {
                new PCB_IO(1,1,new VarType(new RGBA(255,255,0),Float.class).setRegistryName("number"))
        };

        PCB_IO[] simpleDroneIn = {
                new PCB_IO(0,2,new VarType(new RGBA(255,40,255), Vector3f.class).setRegistryName("xyz")),
                new PCB_IO(0,5,new VarType(new RGBA(255,40,255), Vector3f.class).setRegistryName("xyz"))
        };
        PCB_IO[] simpleDroneOut = {
                new PCB_IO(7,2,new VarType(new RGBA(255,40,255), Vector3f.class).setRegistryName("xyz")),
        };
        SMALL_ADD = new SmallAddComponent(3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33), new PCBSymbol(1,0.5F,0,16), DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_add");
        SMALL_SUB = new DefaultPCBComponent(3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,8,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_subtract");
        SMALL_MULTIPLY = new DefaultPCBComponent(3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,24,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_multiply");
        SMALL_DIVIDE = new DefaultPCBComponent(3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,16,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_divide");
        SMALL_EQUALS = new DefaultPCBComponent(3, 2, simpleIn, simpleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,32,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_equals");
        SIMPLE_DRONE_BRAIN = new DefaultPCBComponent(8, 8, simpleDroneIn, simpleDroneOut, new RGBA(43, 150, 0),new PCBSymbol(3,3,16,32,16,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("simple_drone_brain");

        event.getRegistry().registerAll(SMALL_ADD, SMALL_SUB, SMALL_MULTIPLY, SMALL_DIVIDE, SMALL_EQUALS, SIMPLE_DRONE_BRAIN);
    }
    @SubscribeEvent
    public static void PCBRegister(RegistryEvent.NewRegistry event){
        RegistryBuilder<DefaultPCBComponent> builder = new RegistryBuilder<>();
        builder.setType(DefaultPCBComponent.class);
        ResourceLocation key = new ResourceLocation(dronecraft_2Main.MOD_ID, "pcb_components");
        builder.setName(key);
        builder.setDefaultKey(key);
        builder.create();

    }
}

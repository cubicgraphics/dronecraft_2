package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.*;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;


@ObjectHolder(dronecraft_2Main.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PCBComponents {

    public static DefaultPCBComponent SMALL_ADD;
    public static DefaultPCBComponent SMALL_SUB;
    public static DefaultPCBComponent SMALL_MULTIPLY;
    public static DefaultPCBComponent SMALL_DIVIDE;
    public static DefaultPCBComponent SMALL_EQUALS;
    public static DefaultPCBComponent TIME_SENSOR;
    public static DefaultPCBComponent STATIC_BOOL;
    public static DefaultPCBComponent OUTPUT_BOOL;
    public static DefaultPCBComponent OUTPUT_NUMBER;
    public static DefaultPCBComponent SIMPLE_DRONE_BRAIN;


    @SubscribeEvent
    public static void onPCBRegistry(final RegistryEvent.Register<DefaultPCBComponent> event) {
        PCB_IO[] simpleDoubleIn = {
                new PCB_IO(0,0,new VarType(new RGBA(255,255,0),Double.class).setRegistryName("number")), //cannot fetch the varType from its registry as it would have not been registered fully yet, but make sure it has the right registry name so it can be correctly fetched.
                new PCB_IO(2,0,new VarType(new RGBA(255,255,0),Double.class).setRegistryName("number"))
        };
        PCB_IO[] simpleDoubleOut = {
                new PCB_IO(1,1,new VarType(new RGBA(255,255,0),Double.class).setRegistryName("number"))
        };
        PCB_IO[] simpleWildIn = {
                new PCB_IO(0,0,new VarType(new RGBA(220,10,10),Boolean.class).setRegistryName("wild")), //cannot fetch the varType from its registry as it would have not been registered fully yet, but make sure it has the right registry name so it can be correctly fetched.
                new PCB_IO(2,0,new VarType(new RGBA(220,10,10),Boolean.class).setRegistryName("wild"))
        };
        PCB_IO[] simpleBoolOut = {
                new PCB_IO(1,1,new VarType(new RGBA(240,10,10),Boolean.class).setRegistryName("boolean"))
        };
        PCB_IO[] simpleBoolIn = {
                new PCB_IO(0,0,new VarType(new RGBA(240,10,10),Boolean.class).setRegistryName("boolean"))
        };
        PCB_IO[] singleDoubleIn = {
                new PCB_IO(0,0,new VarType(new RGBA(255,255,0),Double.class).setRegistryName("number"))
        };

        SMALL_ADD = new SmallAddComponent(3, 2, simpleWildIn, simpleDoubleOut, new RGBA(0, 130, 33), new PCBSymbol(1,0.5F,0,16), DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_add");
        SMALL_SUB = new SmallSubtractComponent(3, 2, simpleDoubleIn, simpleDoubleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,8,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_subtract");
        SMALL_MULTIPLY = new SmallMultiplyComponent(3, 2, simpleDoubleIn, simpleDoubleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,24,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_multiply");
        SMALL_DIVIDE = new SmallDivideComponent(3, 2, simpleDoubleIn, simpleDoubleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,16,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_divide");
        SMALL_EQUALS = new SmallEqualsComponent(3, 2, simpleWildIn, simpleBoolOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,32,16),DefaultPCBComponent.TYPE.PROCESS).setRegistryName("small_equals");
        TIME_SENSOR = new TimeSensorComponent(3, 2, null, simpleDoubleOut, new RGBA(0, 130, 33),new PCBSymbol(1,0.5F,32,32),DefaultPCBComponent.TYPE.INPUT).setRegistryName("time_sensor");
        STATIC_BOOL = new StaticBoolComponent(3, 2, null, simpleBoolOut, new RGBA(0, 130, 33),DefaultPCBComponent.TYPE.INPUT).setRegistryName("static_bool");
        OUTPUT_BOOL = new DefaultPCBComponent(2, 2, simpleBoolIn, null, new RGBA(0, 130, 33),new PCBSymbol(0.5F,0.5F,24,24),DefaultPCBComponent.TYPE.OUTPUT).setRegistryName("bool_output");
        OUTPUT_NUMBER = new DefaultPCBComponent(2, 2, singleDoubleIn, null, new RGBA(0, 130, 33),new PCBSymbol(0.5F,0.5F,24,24),DefaultPCBComponent.TYPE.OUTPUT).setRegistryName("number_output");

        event.getRegistry().registerAll(SMALL_ADD, SMALL_SUB, SMALL_MULTIPLY, SMALL_DIVIDE, SMALL_EQUALS,TIME_SENSOR,STATIC_BOOL,OUTPUT_BOOL,OUTPUT_NUMBER);
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

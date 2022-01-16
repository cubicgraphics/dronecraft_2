package net.cubic.dronecraft_2.dronecraft_2.data.PCB;

import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
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

    public static PCBComponent SMALL_ADD;
    public static PCBComponent SMALL_SUB;
    public static PCBComponent SMALL_MULTIPLY;
    public static PCBComponent SMALL_DIVIDE;
    public static PCBComponent SMALL_EQUALS;
    public static PCBComponent SIMPLE_DRONE_BRAIN;



    @SubscribeEvent
    public static void onPCBRegistry(final RegistryEvent.Register<PCBComponent> event) {
        SMALL_ADD = new PCBComponent(3, 2, simpleIn.clone(), simpleOut.clone(), new RGBA(0, 130, 33), "add",new PCBSymbol(1,0.5F,0,16)).setRegistryName("small_add");
        SMALL_SUB = new PCBComponent(3, 2, simpleIn.clone(), simpleOut.clone(), new RGBA(0, 130, 33), "sub",new PCBSymbol(1,0.5F,8,16)).setRegistryName("small_subtract");
        SMALL_MULTIPLY = new PCBComponent(3, 2, simpleIn.clone(), simpleOut.clone(), new RGBA(0, 130, 33), "multiply",new PCBSymbol(1,0.5F,24,16)).setRegistryName("small_multiply");
        SMALL_DIVIDE = new PCBComponent(3, 2, simpleIn.clone(), simpleOut.clone(), new RGBA(0, 130, 33), "divide",new PCBSymbol(1,0.5F,16,16)).setRegistryName("small_divide");
        SMALL_EQUALS = new PCBComponent(3, 2, simpleIn.clone(), simpleOut.clone(), new RGBA(0, 130, 33), "equals",new PCBSymbol(1,0.5F,32,16)).setRegistryName("small_equals");
        SIMPLE_DRONE_BRAIN = new PCBComponent(8, 8, simpleDroneIn.clone(), simpleDroneOut.clone(), new RGBA(43, 150, 0),new PCBSymbol(3,3,16,32,16,16)).setRegistryName("simple_drone_brain");

        event.getRegistry().registerAll(SMALL_ADD, SMALL_SUB, SMALL_MULTIPLY, SMALL_DIVIDE, SMALL_EQUALS, SIMPLE_DRONE_BRAIN);
    }
    @SubscribeEvent
    public static void PCBRegister(RegistryEvent.NewRegistry event){
        RegistryBuilder<PCBComponent> builder = new RegistryBuilder<>();
        builder.setType(PCBComponent.class);
        ResourceLocation key = new ResourceLocation(dronecraft_2Main.MOD_ID, "pcb_components");
        builder.setName(key);
        builder.setDefaultKey(key);
        builder.create();

    }
}

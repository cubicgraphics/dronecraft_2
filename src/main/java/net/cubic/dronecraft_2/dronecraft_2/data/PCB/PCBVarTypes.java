package net.cubic.dronecraft_2.dronecraft_2.data.PCB;


import net.cubic.dronecraft_2.dronecraft_2.Utill.RGBA;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryBuilder;

@ObjectHolder(dronecraft_2Main.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PCBVarTypes {

    public static VarType NUMBER;
    public static VarType BOOLEAN;
    public static VarType XYZ;
    public static VarType WILD;

    @SubscribeEvent
    public static void onPCBRegistry(final RegistryEvent.Register<VarType> event) {
        NUMBER = new VarType(new RGBA(255,255,0),Double.class).setRegistryName("number");//TODO add custom wire items that can be used to craft them except boolean
        BOOLEAN = new VarType(new RGBA(240,10,10),Boolean.class).setRegistryName("boolean");
        XYZ = new VarType(new RGBA(255,40,255), Vector3f.class).setRegistryName("xyz");
        WILD = new VarType(new RGBA(220,10,10),Boolean.class).setRegistryName("wild"); //don't give this a crafting recipe or directly use it

        event.getRegistry().registerAll(NUMBER,BOOLEAN,XYZ);
    }
    @SubscribeEvent
    public static void PCBRegister(RegistryEvent.NewRegistry event){
        RegistryBuilder<VarType> builder = new RegistryBuilder<>();
        builder.setType(VarType.class);
        ResourceLocation key = new ResourceLocation(dronecraft_2Main.MOD_ID, "pcb_var_types");
        builder.setName(key);
        builder.setDefaultKey(key);
        builder.create();
    }
}

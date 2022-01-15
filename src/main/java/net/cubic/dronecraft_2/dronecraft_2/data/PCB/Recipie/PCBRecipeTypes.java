package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class PCBRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, dronecraft_2Main.MOD_ID);

    public static final RegistryObject<PCBComponentRecipe.Serializer> PCB_COMPONENT_RECIPE_SERIALIZER
            = RECIPE_SERIALIZER.register("pcb_component", PCBComponentRecipe.Serializer::new);       //TODO wires should use craftable wire items from mod(components will not use these use, that way no conflicts when checking recipes)
    public static final RegistryObject<PCBWireRecipe.Serializer> PCB_WIRE_RECIPE_SERIALIZER
            = RECIPE_SERIALIZER.register("pcb_wire", PCBWireRecipe.Serializer::new);

    public static IRecipeType<PCBComponentRecipe> PCB_COMPONENT_RECIPE
            = new PCBComponentRecipe.ComponentRecipeType();
    public static IRecipeType<PCBWireRecipe> PCB_WIRE_RECIPE
            = new PCBWireRecipe.WireRecipeType();


    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, PCBComponentRecipe.TYPE_ID, PCB_COMPONENT_RECIPE);
        Registry.register(Registry.RECIPE_TYPE, PCBWireRecipe.TYPE_ID, PCB_WIRE_RECIPE);

    }
}

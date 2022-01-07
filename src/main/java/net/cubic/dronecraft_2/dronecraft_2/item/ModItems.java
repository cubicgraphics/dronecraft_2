package net.cubic.dronecraft_2.dronecraft_2.item;

import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;

import net.cubic.dronecraft_2.dronecraft_2.item.circuits.PrintedCircuitBoard;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, dronecraft_2Main.MOD_ID);

    public static final RegistryObject<Item> CONDUCTIUM_DUST = ITEMS.register("conductium_dust",
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> CARBON_CONDUCTIUM_COMPOUND = ITEMS.register("carbon_conductium_compound",
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> CONDUCTIUM_INGOT = ITEMS.register("conductium_ingot",
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> CARBON_CONDUCTIUM_INGOT = ITEMS.register("carbon_conductium_ingot",
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> CONDUCTIVE_MAGLEV = ITEMS.register("conductive_maglev",
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> SUPERCONDUCTIVE_MAGLEV = ITEMS.register("super_conductive_maglev",
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));

    public static final RegistryObject<Item> PrintedCircuitBoard = ITEMS.register("printed-circuit-board",
            () -> new PrintedCircuitBoard(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));



    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }
}

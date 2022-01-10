package net.cubic.dronecraft_2.dronecraft_2.item;

import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;

import net.cubic.dronecraft_2.dronecraft_2.item.circuits.PCBLayered;
import net.cubic.dronecraft_2.dronecraft_2.item.circuits.PCBSubstrate;
import net.cubic.dronecraft_2.dronecraft_2.item.circuits.PCBTemplate;
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


    public static final RegistryObject<Item> LOW_PURITY_SILICON = ITEMS.register("low_purity_silicon", //gathered by crushing quatz
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> HIGH_PURITY_SILICON = ITEMS.register("high_purity_silicon", //silica and sand to temperatures as high as 1800˚C
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> SILICON_INGOT = ITEMS.register("silicon_ingot", // two low purity silica makes one silicon ingot when melted in smeltry - one high purity silicon
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> CRYSTALLISED_SILICON = ITEMS.register("crystallised_silicon", // made in a crystalliser with high temperatures
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> SILICON_WAFER = ITEMS.register("silicon_wafer", //make 4 from one ingot or 8 from one crystallised silicon - made in cutter thing
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> CUT_SILICON_WAFER = ITEMS.register("cut_silicon_wafer", // - put silicon wafer in cutter thing to make 1 wafer into 8 cut silicon wafer
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));

    public static final RegistryObject<Item> HIGH_PURITY_REDSTONE_DUST = ITEMS.register("high_purity_redstone_dust", //refine redstone dust in similar way to high purity silicon
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> CRYSTALLISED_REDSTONE = ITEMS.register("crystallised_redstone", //made in a crystalliser with high temperatures
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    public static final RegistryObject<Item> REDSTONE_WAFER = ITEMS.register("redstone_wafer", //made in cutter - from crystallised redstone or from a redstone block
            () -> new Item(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));


    public static final RegistryObject<Item> PrintedCircuitBoard = ITEMS.register("printed-circuit-board",
            () -> new PrintedCircuitBoard(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));

    public static final RegistryObject<Item> PCBTemplate = ITEMS.register("pcb-template",
            () -> new PCBTemplate(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));

    public static final RegistryObject<Item> PCBSubstrate = ITEMS.register("pcb-substrate",
            () -> new PCBSubstrate(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));

    public static final RegistryObject<Item> PCBLayered = ITEMS.register("pcb-layered",
            () -> new PCBLayered(new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));


    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }
}

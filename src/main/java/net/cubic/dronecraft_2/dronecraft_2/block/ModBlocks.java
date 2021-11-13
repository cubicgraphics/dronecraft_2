package net.cubic.dronecraft_2.dronecraft_2.block;

import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItemGroup;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, dronecraft_2Main.MOD_ID);

    public static final RegistryObject<Block> CONDUCTIUM_BLOCK = registerBlock("conductium_block",
            () -> new Block(AbstractBlock.Properties.create(Material.IRON)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(7f)));




    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);//registers block
        registerBlockItem(name, toReturn);//registers item by calling code beneath
        return toReturn;
    }
    private static <T extends  Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.DRONECRAFT2_GROUP)));
    }//registers an item for the block

    public static void register(IEventBus eventbus){
        BLOCKS.register(eventbus);
    }
}

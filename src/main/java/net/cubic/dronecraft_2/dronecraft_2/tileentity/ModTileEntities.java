package net.cubic.dronecraft_2.dronecraft_2.tileentity;

import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, dronecraft_2Main.MOD_ID);

    public static RegistryObject<TileEntityType<AreaScannerTileEntity>> Area_Scanner_Tile_Entity =
            TILE_ENTITIES.register("area_scanner_tile_entity", ()-> TileEntityType.Builder.create(
                    AreaScannerTileEntity::new, ModBlocks.AREA_SCANNER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        TILE_ENTITIES.register(eventBus);
    }
}

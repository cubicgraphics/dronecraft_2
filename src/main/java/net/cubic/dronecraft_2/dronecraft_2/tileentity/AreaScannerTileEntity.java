package net.cubic.dronecraft_2.dronecraft_2.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class AreaScannerTileEntity extends TileEntity {

    public AreaScannerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public AreaScannerTileEntity(){
        this(ModTileEntities.Area_Scanner_Tile_Entity.get());
    }


}

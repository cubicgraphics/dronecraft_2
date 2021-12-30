package net.cubic.dronecraft_2.dronecraft_2.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class PCBCrafterTileEntity extends TileEntity {

    public PCBCrafterTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public PCBCrafterTileEntity(){
        this(ModTileEntities.PCB_crafter_Tile_Entity.get());
    }


}

package net.cubic.dronecraft_2.dronecraft_2.tileentity;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB.CapabilityPCB;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PCBCrafterTileEntity extends TileEntity {

    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);



    public PCBCrafterTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public PCBCrafterTileEntity(){
        this(ModTileEntities.PCB_crafter_Tile_Entity.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        super.read(state, nbt);
    }
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        return super.write(compound);
    }


    private ItemStackHandler createHandler(){
        return new ItemStackHandler(1){
            @Override
            protected void onContentsChanged(int slot){
                markDirty();
            }
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack itemStack){
                    return (itemStack.getItem() == ModItems.PCBSubstrate.get() && slot == 0) || (itemStack.getItem() == ModItems.PrintedCircuitBoard.get() && slot == 0); //TODO if is a printed circuit board then allow it to be edited if its within the max size
            }

            @Override
            public int getSlotLimit(int slot){
                return 1;
            }
            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack itemStack, boolean simulate){
                if(!isItemValid(slot,itemStack)){
                    return itemStack;
                }
                return super.insertItem(slot,itemStack,simulate);
            }
        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
}

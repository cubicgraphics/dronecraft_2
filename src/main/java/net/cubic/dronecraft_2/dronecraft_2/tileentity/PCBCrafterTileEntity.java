package net.cubic.dronecraft_2.dronecraft_2.tileentity;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBComponentRecipe;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBRecipeTypes;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBWireRecipe;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return new ItemStackHandler(17){
            @Override
            protected void onContentsChanged(int slot){
                markDirty();
            }
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack itemStack){
                return (itemStack.getItem() == ModItems.PCBSubstrate.get() && slot == 0) || (itemStack.getItem() == ModItems.PrintedCircuitBoard.get() && slot == 0 || slot > 0);
            }

            @Override
            public int getSlotLimit(int slot){
                if(slot == 0){
                    return 1;
                }
                else{
                    return 64;
                }
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

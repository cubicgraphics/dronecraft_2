package net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//I believe this is for saving the compound nbt data
public class PCBProvider implements ICapabilitySerializable<CompoundNBT> {

    private final DefaultPCB PcbData = new DefaultPCB();
    private final LazyOptional<IPCB> PcbDataOptional = LazyOptional.of(() -> PcbData);

    public void invalidate() {PcbDataOptional.invalidate(); }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return PcbDataOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CapabilityPCB.PCB_DATA == null){
            return new CompoundNBT();
        }
        else{
            return (CompoundNBT) CapabilityPCB.PCB_DATA.writeNBT(PcbData, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CapabilityPCB.PCB_DATA != null){
            CapabilityPCB.PCB_DATA.readNBT(PcbData, null, nbt);
        }

    }
}

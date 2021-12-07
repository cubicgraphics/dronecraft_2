package net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//I believe this is for saving the compound nbt data
public class ScannerAreaProvider implements ICapabilitySerializable<CompoundNBT> {

    private final DefaultScannerArea scannerarea = new DefaultScannerArea();
    private final LazyOptional<IScannerArea> scannerAreaOptional = LazyOptional.of(() -> scannerarea);

    public void invalidate() {scannerAreaOptional.invalidate(); }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return scannerAreaOptional.cast();
    }

    @Override
    public CompoundNBT serializeNBT() {
        if (CapabilityScannerArea.SCANNER_AREA == null){
            return new CompoundNBT();
        }
        else{
            return (CompoundNBT) CapabilityScannerArea.SCANNER_AREA.writeNBT(scannerarea, null);
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (CapabilityScannerArea.SCANNER_AREA != null){
            CapabilityScannerArea.SCANNER_AREA.readNBT(scannerarea, null, nbt);
        }

    }
}

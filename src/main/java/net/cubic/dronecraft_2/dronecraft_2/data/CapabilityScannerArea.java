package net.cubic.dronecraft_2.dronecraft_2.data;

import net.minecraft.nbt.*;
import net.minecraft.util.Direction;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.List;

public class CapabilityScannerArea {

    @CapabilityInject(IScannerArea.class)
    public static Capability<IScannerArea> SCANNER_AREA = null;

    public static void register(){
        System.out.println("Dronecraft_2 registering scanner area capability");
        CapabilityManager.INSTANCE.register(IScannerArea.class, new storage(), DefaultScannerArea::new);
    }

    public static class storage implements Capability.IStorage<IScannerArea>{


        @Nullable
        @Override
        public INBT writeNBT(Capability<IScannerArea> capability, IScannerArea instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            ListNBT scannerList = new ListNBT();

            for (int i = 0; i < instance.GetScannerAreas().size(); i++) {
                CompoundNBT scannerdata = new CompoundNBT();
                scannerdata.put("BlockPos", NBTUtil.writeBlockPos(instance.GetScannerAreas().get(i).ScannerPos));//sets the blockpos to compound nbt
                scannerdata.putInt("Range", instance.GetScannerAreas().get(i).Range);//sets the range to compound nbt
                scannerList.add(scannerdata);
            }
            tag.put("ScannerList", scannerList);

            return tag;
        }

        @Override
        public void readNBT(Capability<IScannerArea> capability, IScannerArea instance, Direction side, INBT nbt) {

            ListNBT taglist = (ListNBT) ((CompoundNBT) nbt).get("ScannerList"); //reads the blockpos from compound nbt
            List<ScannerFormat> scannerList = null;
            for (int i = 0; i < taglist.size(); i++) {
                CompoundNBT scannerData = taglist.getCompound(i);
                scannerList.add(new ScannerFormat(NBTUtil.readBlockPos((scannerData).getCompound("BlockPos")), ((scannerData).getInt("Range"))));
            }
            instance.SetScanners(scannerList);
        }
    }

}

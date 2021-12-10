package net.cubic.dronecraft_2.dronecraft_2.Utill.network.packets.to_client;


import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.CapabilityScannerArea;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.ScannerAreaProvider;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaCapability.ScannerFormat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SyncScannerAreaCapability {

    private final List<ScannerFormat> ScannerList;

    public SyncScannerAreaCapability(PacketBuffer buf) {
        ListNBT tag = (ListNBT) buf.readCompoundTag().get("ScannerList");
        List<ScannerFormat> scannerList = new ArrayList<>();
        if (tag != null){
            for (int i = 0; i < tag.size(); i++) {
                CompoundNBT scannerData = tag.getCompound(i);
                scannerList.add(new ScannerFormat(NBTUtil.readBlockPos((scannerData).getCompound("BlockPos")), ((scannerData).getInt("Range")),((scannerData).getInt("AreaMode"))));
                System.out.println(scannerList.get(scannerList.size()-1).ScannerPos.toString());
            }
        }
        ScannerList = scannerList;
    }

    public SyncScannerAreaCapability(List<ScannerFormat> scannerList){
        this.ScannerList = scannerList;
    }


    public void toBytes(PacketBuffer buf) {
        CompoundNBT tag = new CompoundNBT();
        ListNBT scannerList = new ListNBT();
        if (ScannerList != null) {
            for (int i = 0; i < ScannerList.size(); i++) {
                CompoundNBT scannerdata = new CompoundNBT();
                scannerdata.put("BlockPos", NBTUtil.writeBlockPos(ScannerList.get(i).ScannerPos));//sets the blockpos to compound nbt
                scannerdata.putInt("Range", ScannerList.get(i).Range);//sets the range to compound nbt
                scannerdata.putInt("AreaMode", ScannerList.get(i).AreaMode);
                scannerList.add(scannerdata);
            }
        }
        tag.put("ScannerList", scannerList);
        buf.writeCompoundTag(tag);
    }


    public boolean handle(Supplier<NetworkEvent.Context> ctx ){
        ctx.get().enqueueWork(() -> {
            ctx
                    .get()
                    .getSender()//TODO returning null here - need to save the capability to the client side of the game so it can be read
                    .world
                    .getCapability(
                            CapabilityScannerArea
                                    .SCANNER_AREA)
                    .ifPresent(
                            h -> {
                h.SetScanners(ScannerList);
                System.out.println("has recieved and written NBT data to client side world");
            });
        });

        return true;
    }

}



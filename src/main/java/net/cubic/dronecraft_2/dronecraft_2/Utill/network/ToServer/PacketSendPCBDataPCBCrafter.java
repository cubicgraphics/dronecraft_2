package net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB.CapabilityPCB;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItems;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

public class PacketSendPCBDataPCBCrafter {

    private final int SlotID;
    private final PCBData pcbData;
    private final BlockPos blockPos;

    public PacketSendPCBDataPCBCrafter(int slotID, PCBData pcBData, BlockPos blockPosition){
        this.SlotID =  slotID;
        this.pcbData = pcBData;
        this.blockPos = blockPosition;
    }

    public PacketSendPCBDataPCBCrafter(PacketBuffer buf){
        SlotID = buf.readInt();
        blockPos = buf.readBlockPos();
        pcbData = PCBData.ReadNBT(buf.readCompoundTag());

    }

    public void toBytes(PacketBuffer buf){
        buf.writeInt(SlotID);
        buf.writeBlockPos(blockPos);
        if(pcbData == null){
            PCBData newData = new PCBData();
            buf.writeCompoundTag((CompoundNBT) newData.ToNBT());
        }
        else {
            buf.writeCompoundTag((CompoundNBT) pcbData.ToNBT());
        }
    }


    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            ctx.get().getSender().world.getTileEntity(blockPos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->{
                if(h.getStackInSlot(SlotID).getItem() == ModItems.PrintedCircuitBoard.get()){
                    h.getStackInSlot(SlotID).getCapability(CapabilityPCB.PCB_DATA).ifPresent(e ->{
                        e.setPCBData(pcbData);
                    });
                }
                else if(h.getStackInSlot(SlotID).getItem() == ModItems.PCBSubstrate.get()){
                    h.extractItem(SlotID,1,false);
                    h.insertItem(SlotID,ModItems.PrintedCircuitBoard.get().getDefaultInstance(),false);
                    h.getStackInSlot(SlotID).getCapability(CapabilityPCB.PCB_DATA).ifPresent(e ->{
                        e.setPCBData(pcbData);
                    });
                }
                else{
                    System.out.println("message was sent but criteria not matched");
                }
            });
                });
        return true;
    }
}



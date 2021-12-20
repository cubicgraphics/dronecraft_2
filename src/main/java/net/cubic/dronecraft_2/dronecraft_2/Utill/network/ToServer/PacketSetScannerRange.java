package net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer;

import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSetScannerRange {

    private final int NewRange;
    private final BlockPos blockPos;

    public PacketSetScannerRange(BlockPos blockpos, int newRange){
        this.blockPos =  blockpos;
        this.NewRange = newRange;
    }

    public PacketSetScannerRange(PacketBuffer buf){
        blockPos = buf.readBlockPos();
        NewRange = buf.readInt();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeBlockPos(blockPos);
        buf.writeInt(NewRange);
    }


    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            World world = ctx.get().getSender().world;

            WorldGlobalVar.WorldVariables.get(world).Scanners.SetScannerRange(blockPos,NewRange);
            WorldGlobalVar.WorldVariables.get(world).syncData(world);// To write to world data
            System.out.println("Set scanner range and synced from handler");
                });
        return true;
    }
}

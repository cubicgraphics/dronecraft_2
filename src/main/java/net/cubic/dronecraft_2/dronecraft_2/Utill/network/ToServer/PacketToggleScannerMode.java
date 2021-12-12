package net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer;

import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill.ScannerFormat;
import net.cubic.dronecraft_2.dronecraft_2.data.WorldGlobalVar;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketToggleScannerMode {

    private final BlockPos blockPos;

    public PacketToggleScannerMode(BlockPos blockpos){
        this.blockPos =  blockpos;
    }

    public PacketToggleScannerMode(PacketBuffer buf){
        blockPos = buf.readBlockPos();
    }

    public void toBytes(PacketBuffer buf){
        buf.writeBlockPos(blockPos);
    }



    public boolean handle(Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(()->{
            World world = ctx.get().getSender().world;


            int scannerMode = WorldGlobalVar.WorldVariables.get(world).Scanners.GetAreaMode(blockPos);
            if (scannerMode >= 1){
                scannerMode = 0;
            }
            else {
                scannerMode++;
            }
            WorldGlobalVar.WorldVariables.get(world).Scanners.SetAreaMode(blockPos,scannerMode);
            WorldGlobalVar.WorldVariables.get(world).syncData(world);// To write to world data
                });
        return true;
    }
}

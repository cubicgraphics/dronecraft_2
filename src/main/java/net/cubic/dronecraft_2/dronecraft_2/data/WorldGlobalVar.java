package net.cubic.dronecraft_2.dronecraft_2.data;

import net.cubic.dronecraft_2.dronecraft_2.Utill.network.PacketHandler;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill.ScannerAreaData;
import net.cubic.dronecraft_2.dronecraft_2.data.ScannerAreaUtill.ScannerFormat;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashMap;
import java.util.function.Supplier;

public class WorldGlobalVar {


    public WorldGlobalVar(PacketHandler handler) {
        handler.addNetworkMessage(WorldGlobalVar.WorldSavedDataSyncMessage.class, WorldGlobalVar.WorldSavedDataSyncMessage::buffer, WorldGlobalVar.WorldSavedDataSyncMessage::new, WorldGlobalVar.WorldSavedDataSyncMessage::handler);
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            WorldSavedData mapdata = MapVariables.get(event.getPlayer().world);
            WorldSavedData worlddata = WorldVariables.get(event.getPlayer().world);
            if (mapdata != null)
                PacketHandler.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
                        new WorldSavedDataSyncMessage(0, mapdata));
            if (worlddata != null)
                PacketHandler.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
                        new WorldSavedDataSyncMessage(1, worlddata));
        }
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().world.isRemote()) {
            WorldSavedData worlddata = WorldVariables.get(event.getPlayer().world);
            if (worlddata != null)
                PacketHandler.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
                        new WorldSavedDataSyncMessage(1, worlddata));
        }
    }
    public static class WorldVariables extends WorldSavedData {
        public static final String DATA_NAME = "dronecraft_2_worldvars";
        public ScannerAreaData Scanners = new ScannerAreaData();

        public WorldVariables() {
            super(DATA_NAME);
        }

        public WorldVariables(String s) {
            super(s);
        }

        @Override
        public void read(CompoundNBT nbt) {
            //read scanners from nbt here
            System.out.println("Reading Dronecraft scanner data from world");
            ListNBT tagList = (ListNBT) nbt.get("ScannerList"); //gets the list from compound nbt
            HashMap<BlockPos,ScannerFormat> scannerList = new HashMap<>();
            if (tagList != null){
                for (int i = 0; i < tagList.size(); i++) {
                    CompoundNBT scannerData = tagList.getCompound(i);
                    scannerList.put(NBTUtil.readBlockPos((scannerData).getCompound("BlockPos")),new ScannerFormat(((scannerData).getInt("Range")),((scannerData).getInt("AreaMode"))));
                }
            }
            Scanners.SetScanners(scannerList);
        }

        @Override
        public CompoundNBT write(CompoundNBT nbt) {
            ListNBT scannerListNBT = new ListNBT();
            HashMap<BlockPos,ScannerFormat> scannerList = Scanners.GetScanners();
            if (scannerList != null) {
                for (BlockPos i : scannerList.keySet()) {
                    CompoundNBT scannerdata = new CompoundNBT();
                    scannerdata.put("BlockPos", NBTUtil.writeBlockPos(i));//sets the blockpos to compound nbt
                    scannerdata.putInt("Range", scannerList.get(i).Range);//sets the range to compound nbt
                    scannerdata.putInt("AreaMode", scannerList.get(i).AreaMode);
                    scannerListNBT.add(scannerdata);
                }
            }
            nbt.put("ScannerList", scannerListNBT);
            return nbt;
        }

        public void syncData(World world) {
            this.markDirty();
            if (world != null && !world.isRemote())
                PacketHandler.PACKET_HANDLER.send(PacketDistributor.DIMENSION.with(((World) world)::getDimensionKey),
                        new WorldSavedDataSyncMessage(1, this));
        }
        static WorldVariables clientSide = new WorldVariables();

        public static WorldVariables get(World world) {
            if (world instanceof ServerWorld) {
                return ((ServerWorld) world).getSavedData().getOrCreate(WorldVariables::new, DATA_NAME);
            } else {
                return clientSide;
            }
        }
    }

    public static class MapVariables extends WorldSavedData {
        public static final String DATA_NAME = "dronecraft_2_mapvars";

        //global across dimensions


        public MapVariables() {
            super(DATA_NAME);
        }

        public MapVariables(String s) {
            super(s);
        }

        @Override
        public void read(CompoundNBT nbt) {
        }

        @Override
        public CompoundNBT write(CompoundNBT nbt) {
            return nbt;
        }

        public void syncData(World world) {
            this.markDirty();
            if (world != null && !world.isRemote())
                PacketHandler.PACKET_HANDLER.send(PacketDistributor.ALL.noArg(), new WorldSavedDataSyncMessage(0, this));
        }
        static MapVariables clientSide = new MapVariables();
        public static MapVariables get(World world) {
            if (world instanceof ServerWorld) {
                return ((ServerWorld) world).getWorld().getServer().getWorld(World.OVERWORLD).getSavedData().getOrCreate(MapVariables::new,
                        DATA_NAME);
            } else {
                return clientSide;
            }
        }
    }

    public static class WorldSavedDataSyncMessage {
        public int type;
        public WorldSavedData data;
        public WorldSavedDataSyncMessage(PacketBuffer buffer) {
            this.type = buffer.readInt();
            this.data = this.type == 0 ? new MapVariables() : new WorldVariables();
            this.data.read(buffer.readCompoundTag());
        }

        public WorldSavedDataSyncMessage(int type, WorldSavedData data) {
            this.type = type;
            this.data = data;
        }

        public static void buffer(WorldSavedDataSyncMessage message, PacketBuffer buffer) {
            buffer.writeInt(message.type);
            buffer.writeCompoundTag(message.data.write(new CompoundNBT()));
        }

        public static void handler(WorldSavedDataSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    if (message.type == 0)
                        MapVariables.clientSide = (MapVariables) message.data;
                    else
                        WorldVariables.clientSide = (WorldVariables) message.data;
                }
            });
            context.setPacketHandled(true);
        }
    }
}

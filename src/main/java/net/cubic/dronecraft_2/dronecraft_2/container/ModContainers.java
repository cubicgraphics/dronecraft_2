package net.cubic.dronecraft_2.dronecraft_2.container;

import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
    public static DeferredRegister<ContainerType<?>> CONTAINERS
            = DeferredRegister.create(ForgeRegistries.CONTAINERS, dronecraft_2Main.MOD_ID);

    public static final RegistryObject<ContainerType<AreaScannerContainer>> AREA_SCANNER_CONTAINER
            = CONTAINERS.register("area_scanner_container", ()->
            IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new AreaScannerContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<PCBCrafterContainer>> PCB_CRAFTER_CONTAINER
            = CONTAINERS.register("pcb_crafter_container", ()->
            IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new PCBCrafterContainer(windowId, world, pos, inv, inv.player);
            })));


    public static void register(IEventBus eventBus){
        CONTAINERS.register(eventBus);
    }

}

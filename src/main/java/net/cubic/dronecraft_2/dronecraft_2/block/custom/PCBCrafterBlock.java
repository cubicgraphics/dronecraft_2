package net.cubic.dronecraft_2.dronecraft_2.block.custom;

import net.cubic.dronecraft_2.dronecraft_2.container.PCBCrafterContainer;
import net.cubic.dronecraft_2.dronecraft_2.tileentity.ModTileEntities;
import net.cubic.dronecraft_2.dronecraft_2.tileentity.PCBCrafterTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class PCBCrafterBlock extends Block {

    public PCBCrafterBlock(Properties properties) {
        super(properties
                .hardnessAndResistance(6f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .setRequiresTool()
        );
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote()){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (!player.isCrouching()){
                if (tileEntity instanceof PCBCrafterTileEntity){
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn,pos);

                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider,tileEntity.getPos());
                }
                else{
                    throw new IllegalStateException("Our container provider is missing");
                }
            }
        }

        return ActionResultType.SUCCESS;
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.dronecraft_2.pcb_crafter-gui");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity player) {
                return new PCBCrafterContainer(i, worldIn,pos,playerInventory,player);
            }
        };
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return ModTileEntities.PCB_crafter_Tile_Entity.get().create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}

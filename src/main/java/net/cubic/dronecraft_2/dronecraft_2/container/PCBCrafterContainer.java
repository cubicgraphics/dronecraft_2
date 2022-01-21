package net.cubic.dronecraft_2.dronecraft_2.container;


import net.cubic.dronecraft_2.dronecraft_2.ModSettings;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.PacketHandler;
import net.cubic.dronecraft_2.dronecraft_2.Utill.network.ToServer.PacketSendPCBDataPCBCrafter;
import net.cubic.dronecraft_2.dronecraft_2.block.ModBlocks;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.DefaultPCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBComponentXY;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBtest;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBComponentRecipe;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBRecipeTypes;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBWireRecipe;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.data.capabilities.PCB.CapabilityPCB;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PCBCrafterContainer extends Container {
    private final TileEntity tileEntity;
    private final PlayerEntity playerEntity;
    private final IItemHandler playerInventory;

    private int GuiScale;
    public final int LeftPCBGrid = 17;
    public final int TopPCBGrid = 111;
    public final int PCBGridTileWidth = 16; //size of the pcb tile grid(*8 for size in pixels) width is X in this case
    public final int PCBGridTileLength = 16;  //TODO add in pcb template crafter block(more advanced version of this but instead makes templates to be used in machines to make larger more complex pcb's)
    public final int PCBMaxTileWidth = 16; //max size of pcb allowed to be edited
    public final int PCBMaxTileLength = 16;
    public PCBData CurrentPCB = null;
    public PCBData InitialItemPCB = null; //used for calculating change in recipe items needed from the difference in components

    public List<PCBComponentXY<? extends DefaultPCBComponent>> SelectablePCBComponents = new ArrayList<>();
    public final int LeftPCBSelectionBar = 17;
    public final int TopPCBSelectionBar = 19;
    public final int PCBSelectionBarWidth = 128;
    public final int PCBSelectionBarHeight = 52;
    public int SelectablePCBComponentsPixelWidth = 0;
    public int SelectablePCBComponentsPixelHeight = 0;
    public int SelectBoxScrollOffsetX = 0;
    public int SelectBoxScrollOffsetY = 0;

    public List<VarType> SelectablePCBWires = new ArrayList<>();
    public final int LeftWireSelectionBar = 17;
    public final int TopWireSelectionBar = 81;
    public final int SelectableWireBarWidth = 128;
    public final int SelectableWireBarHeight = 8;
    public int SelectableWiresPixelWidth = 0;
    public int SelectableWireScrollOffsetX = 0;
    List<PCBComponentRecipe> ComponentRecipes;




    public PCBCrafterContainer(int WindowID, World worldIn, BlockPos pos, PlayerInventory playerinventory, PlayerEntity player){
        super(ModContainers.PCB_CRAFTER_CONTAINER.get(), WindowID);
        this.tileEntity = worldIn.getTileEntity(pos);
        playerEntity = player;
        this.playerInventory = new InvWrapper(playerinventory);
        layoutPlayerInventorySlots(48,258);
        if(worldIn.isRemote() && ModSettings.GuiRescaling.get()){
            GuiScale = Minecraft.getInstance().gameSettings.guiScale;
            Minecraft.getInstance().gameSettings.guiScale = 2;
            Minecraft.getInstance().updateWindowSize();
        }
        if(tileEntity != null){
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->{
                addSlot(new SlotItemHandler(h,0,73,92)); //adds slots to the gui //this slot is the circuit board slot
                addSlot(new SlotItemHandler(h,1,165,19)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,2,183,19)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,3,201,19)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,4,219,19)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,5,165,37)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,6,183,37)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,7,201,37)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,8,219,37)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,9,165,55)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,10,183,55)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,11,201,55)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,12,219,55)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,13,165,73)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,14,183,73)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,15,201,73)); //adds slots to the gui
                addSlot(new SlotItemHandler(h,16,219,73)); //adds slots to the gui
            } );
        }
        UpdateSelectablePCBComponentsAndWires("");
        ComponentRecipes = new ArrayList<>(Minecraft.getInstance().world.getRecipeManager().getRecipesForType(PCBRecipeTypes.PCB_COMPONENT_RECIPE));
    }

    public void UpdateSelectablePCBComponentsAndWires(String search){
        List<DefaultPCBComponent> components = GetCraftablePCBComponents(search);
        List<PCBComponentXY<? extends DefaultPCBComponent>> componentXYList = new ArrayList<>();
        int pixelsAcross = 0;
        int pixelsDown = 0;
        for (DefaultPCBComponent component : components) {
            componentXYList.add(new PCBComponentXY<>(pixelsAcross, 0, component));
            pixelsAcross = pixelsAcross + 8 + component.Length*8;
            if(pixelsDown < component.Width*8){
                pixelsDown = component.Width*8;
            }
        }
        SelectablePCBWires = GetCraftablePCBWires();
        SelectableWiresPixelWidth = SelectablePCBWires.size()*8;
        SelectablePCBComponentsPixelWidth = pixelsAcross;
        SelectablePCBComponentsPixelHeight = pixelsDown;
        SelectablePCBComponents = componentXYList;
    }



    public List<DefaultPCBComponent> GetCraftablePCBComponents(String search){
        List<PCBComponentRecipe> recipes = new ArrayList<>(tileEntity.getWorld().getRecipeManager().getRecipesForType(PCBRecipeTypes.PCB_COMPONENT_RECIPE));
        List<DefaultPCBComponent> components = new ArrayList<>();
        for (PCBComponentRecipe recipe : recipes) {
            if(recipe.getComponentResult() != null && (Objects.equals(search, "") || recipe.getComponentResult().getName().getString().toLowerCase().contains(search.toLowerCase()) || recipe.getComponentResult().Type.toString().toLowerCase().contains(search.toLowerCase()))){
                components.add(recipe.getComponentResult());
            }
        }
        return components;
    }
    public List<VarType> GetCraftablePCBWires(){
        List<PCBWireRecipe> recipes = tileEntity.getWorld().getRecipeManager().getRecipesForType(PCBRecipeTypes.PCB_WIRE_RECIPE);
        List<VarType> wireTypes = new ArrayList<>();
        for (PCBWireRecipe recipe : recipes) {
            if(recipe.getWireResult() != null){
                wireTypes.add(recipe.getWireResult());
            }
        }
        return wireTypes;
    }

    public List<ItemStack[]> GetRequiredItemsToCraftComponents(PCBData pcb){
        List<ItemStack[]> itemStacks = new ArrayList<>();
        for (int i = 0; i < pcb.ComponentList.size(); i++) {
            List<Ingredient> RecipeIngredients = GetRecipe(pcb.ComponentList.get(i).Component);//TODO the PCB Ingredient list is doubling for some reason every time the solder button is pressed while the gui is open
            List<ItemStack[]> PCBIngredients = new ArrayList<>();
            for (Ingredient ingredient : RecipeIngredients) {
                PCBIngredients.add(ingredient.getMatchingStacks());
            }
            System.out.println(PCBIngredients);
            itemStacks = new ArrayList<>(CombineAndAddItemStacks(itemStacks,PCBIngredients));
        }
        return new ArrayList<>(itemStacks);
    }
    //TODO Move all these Itemstack merging functions into there own class in UTIL;
    public List<ItemStack[]> CombineAndAddItemStacks(List<ItemStack[]> MainStack, List<ItemStack[]> StacksToAdd){
        for (ItemStack[] itemStacks : StacksToAdd) {
            MainStack = IntergrateItemStackIntoList(MainStack, itemStacks);
        }
        return new ArrayList<>(MainStack);
    }

    List<ItemStack[]> IntergrateItemStackIntoList(List<ItemStack[]> MainStack, ItemStack[] StackToAdd){
        int RemainingItems = 64;
        for (int i = 0; i < MainStack.size() && RemainingItems > 0; i++) {
            if(DoStackArraysMatch(MainStack.get(i),StackToAdd)){
                MainStack.set(i,AddItemStacks(MainStack.get(i),StackToAdd));
                RemainingItems = GetIntRemainder(MainStack.get(i),StackToAdd);
                if(RemainingItems > 0){
                    StackToAdd = GetItemStackRemainder(MainStack.get(i),StackToAdd);
                }
            }
        }
        if(RemainingItems > 0){
            MainStack.add(StackToAdd);
        }
        return new ArrayList<>(MainStack);
    }


    /**
     * Checks if the ItemStacks are the same length, contain the same item types and that stack2 can merge with stack1 without overflowing the stacks;
     * @param stack1
     * @param stack2
     * @return
     */
    boolean DoesItemStackFit(ItemStack[] stack1, ItemStack[] stack2){
        return DoStackArraysMatch(stack1, stack2) && GetIntRemainder(stack1, stack2) > 0;
    }

    /**
     *Checks if two ItemStack arrays are the same length and contain the same item types.
     * @param stack1
     * @param stack2
     * @return
     */
    boolean DoStackArraysMatch(ItemStack[] stack1, ItemStack[] stack2){
        if (stack1.length == stack2.length)
            for (int i = 0; i < stack1.length; i++) {
                if(stack1[i].getItem() != stack2[i].getItem()){
                    return false;
                }
            }
        else{
            return false;
        }
        return true;
    }

    /**
     *Presumes that the stack1 and stack2 are the same array size and hold the same item types.
     * returns Math.max((stack1[0].getCount() + stack2[0].getCount()) - stack1[0].getMaxStackSize(),0)
     * @param stack1
     * @param stack2
     * @return
     */
    int GetIntRemainder(ItemStack[] stack1, ItemStack[] stack2){
        return Math.max((stack1[0].getCount() + stack2[0].getCount()) - stack1[0].getMaxStackSize(),0);
    }
    /**
     *Presumes that the stack1 and stack2 are the same array size and hold the same item types.
     * Returns the ItemStack[] of size GetIntRemainder()
     * @param stack1
     * @param stack2
     * @return
     */
    ItemStack[] GetItemStackRemainder(ItemStack[] stack1, ItemStack[] stack2){
        for (ItemStack itemStack : stack1) {
            itemStack.setCount(GetIntRemainder(stack1, stack2));
        }
        return stack1;
    }
    /**
     *Presumes that the stack1 and stack2 are the same array size and hold the same item types.
     * Returns the ItemStack[] of stack1 + stack2 and caps it at the max itemstack size of stack1 if it goes over
     * @param stack1
     * @param stack2
     * @return
     */
    ItemStack[] AddItemStacks(ItemStack[] stack1, ItemStack[] stack2){
        for (int i = 0; i < stack1.length; i++) {
            stack1[i].setCount(Math.min(stack1[i].getCount() + stack2[i].getCount(), stack1[i].getMaxStackSize()));
        }
        return stack1;
    }


    /**
     * Will return the first recipe it finds that outputs the input component
     *
     * @param component
     * @param <T>
     * @return
     */
    public <T extends DefaultPCBComponent> List<Ingredient> GetRecipe(T component){ //for some reason the recipe items inside of ComponentRecipes that are used in the component get multiplied by the amount this function is triggered within a for loop. //TODO AAAAA RECIPE ISSUE HERE

        for (PCBComponentRecipe recipe : ComponentRecipes) {
            if (recipe.getComponentResult() == component) {
                System.out.println("Ingredients directly from recipe manager");
                List<ItemStack[]> itemStacks = new ArrayList<>();
                for (int i = 0; i < recipe.getIngredients().size(); i++) {
                    itemStacks.add(recipe.getIngredients().get(i).getMatchingStacks());
                }
                for (ItemStack[] item : itemStacks) {
                    StringBuilder print = new StringBuilder();
                    for (ItemStack itemStack : item) {
                        print.append(itemStack.toString());
                    }
                    System.out.println(print);
                }

                return new ArrayList<>(recipe.getIngredients());
            }
        }
        return null;
    }


    public void SetDataFromItem(int slotid){
        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h ->{
            if(h.getStackInSlot(slotid) != ItemStack.EMPTY){
                h.getStackInSlot(slotid).getCapability(CapabilityPCB.PCB_DATA).ifPresent(e ->{
                    if(e.getPCBData().length <= PCBMaxTileLength && e.getPCBData().width <= PCBMaxTileWidth){
                        CurrentPCB = e.getPCBData();
                    }
                    else{
                        CurrentPCB = null;
                    }
                });
            }
            else{
                CurrentPCB = null;
            }
            InitialItemPCB = CurrentPCB;
        });
    }

    public PCBData SetAndGetPCBDataFromItem(){
        SetDataFromItem(0);
        return CurrentPCB;
    }

    public List<PCBComponentXY<? extends DefaultPCBComponent>> GetSelectablePCBComponentsList()
    {
        return SelectablePCBComponents;
    }


    public void SetPCBItemData(int SlotId,PCBData pcbData){
        PacketHandler.sendToServer(new PacketSendPCBDataPCBCrafter(SlotId, pcbData,getBlockPos()));
    }

    public void SavePCBToItem(){
        SetPCBItemData(0, CurrentPCB);
        List<ItemStack[]> items = GetRequiredItemsToCraftComponents(SetAndGetPCBDataFromItem());
        for (ItemStack[] item : items) {
            StringBuilder print = new StringBuilder();
            for (ItemStack itemStack : item) {
                print.append(itemStack.toString());
            }
            System.out.println(print);
        }
        System.out.println("Saving new PCBData to item");
    }

    public BlockPos getBlockPos(){
        return tileEntity.getPos();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
        playerIn, ModBlocks.PCB_CRAFTER_BLOCK.get());
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        if(playerIn.world.isRemote() && ModSettings.GuiRescaling.get()){
            Minecraft.getInstance().gameSettings.guiScale = GuiScale;
            Minecraft.getInstance().updateWindowSize();
        }
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 17;  // must match TileEntityInventoryBasic.NUMBER_OF_SLOTS

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot sourceSlot = inventorySlots.get(index);
        if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getStack();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!mergeItemStack(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.putStack(ItemStack.EMPTY);
        } else {
            sourceSlot.onSlotChanged();
        }
        sourceSlot.onTake(playerEntity, sourceStack);
        return copyOfSourceStack;
    }
}

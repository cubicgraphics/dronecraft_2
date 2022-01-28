package net.cubic.dronecraft_2.dronecraft_2.Utill;

import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.DefaultPCBComponent;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.PCBData;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBComponentRecipe;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBRecipeTypes;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie.PCBWireRecipe;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeComponentItemArrayUtil {



    /**
     * @param stack1 ItemStack[]
     * @param stack2 ItemStack[]
     * @return returns true if the ItemStacks are the same length, contain the same item types and that stack2 can merge with stack1 without overflowing the stacks
     */
    public static boolean DoesItemStackFit(ItemStack[] stack1, ItemStack[] stack2){
        return DoStackArraysMatch(stack1, stack2) && GetIntRemainder(stack1, stack2) > 0;
    }

    /**
     * @param pcb PCBData
     * @return Will return a list of ItemStacks[] needed to craft the pcb, each ItemStack[] in the list is either a single item or all the items in a tag.
     */
    public static List<ItemStack[]> GetRequiredItemsToCraftComponents(PCBData pcb){
        List<ItemStack[]> itemStacks = new ArrayList<>();
        if(pcb != null){
            for (int i = 0; i < pcb.ComponentList.size(); i++) {
                itemStacks = AddComponentItemIngredientsToList(itemStacks,pcb.ComponentList.get(i).Component);
            }
            for (int i = 0; i < pcb.PCBWiresArray.length; i++) {
                for (int j = 0; j < pcb.PCBWiresArray[i].length; j++) {
                    if(pcb.PCBWiresArray[i][j] != null){
                        itemStacks = AddWireItemIngredientsToList(itemStacks,pcb.PCBWiresArray[i][j]);
                    }
                }
            }
        }
        return new ArrayList<>(itemStacks);
    }

    //TODO need to make a Remove component ingredient items from ingredient items list - then the PCB crafter container can be optimised to only add/remove from list instead of re-calculating it entirely each time.

    /**
     * @param MainList List of ItemStack[]
     * @param Component extends DefaultPCBComponent
     * @return returns the input list of itemstacks[] with the items needed for the component placed into it
     */
    public static <T extends DefaultPCBComponent> List<ItemStack[]> AddComponentItemIngredientsToList(List<ItemStack[]> MainList, T Component){
        List<Ingredient> RecipeIngredients = GetComponentRecipe(Component);
        List<ItemStack[]> PCBIngredients = new ArrayList<>();
        for (Ingredient ingredient : RecipeIngredients) {
            PCBIngredients.add(ingredient.getMatchingStacks());
        }
        if(MainList == null){
            MainList = new ArrayList<>();
        }
        return CombineAndAddItemStacks(MainList,PCBIngredients);
    }

    /**
     * @param MainList List of ItemStack[]
     * @param Wire extends VarType
     * @return returns the input list of itemstacks[] with the items needed for the wire placed into it
     */
    public static <T extends VarType> List<ItemStack[]> AddWireItemIngredientsToList(List<ItemStack[]> MainList, T Wire){
        List<Ingredient> RecipeIngredients = GetWireRecipe(Wire);
        List<ItemStack[]> PCBIngredients = new ArrayList<>();
        for (Ingredient ingredient : RecipeIngredients) {
            PCBIngredients.add(ingredient.getMatchingStacks());
        }
        if(MainList == null){
            MainList = new ArrayList<>();
        }
        return CombineAndAddItemStacks(MainList,PCBIngredients);
    }



    /**
     * @param MainStack the main list of items
     * @param StacksToAdd a list of items to add to the main list
     * @return the combined list of Mainstack and Stacks to add
     */
    public static List<ItemStack[]> CombineAndAddItemStacks(List<ItemStack[]> MainStack, List<ItemStack[]> StacksToAdd){
        for (ItemStack[] itemStacks : StacksToAdd) {
            MainStack = IntergrateItemStackIntoList(MainStack, itemStacks);
        }
        return new ArrayList<>(MainStack);
    }
    /**
     * @param MainStack list of Itemstacks[]
     * @param StackToAdd An itemStack[] to be added into the MainStack list
     * @return Adds the ItemStack[] into the MainStack list and adds together any that are the same
     */
    public static List<ItemStack[]> IntergrateItemStackIntoList(List<ItemStack[]> MainStack, ItemStack[] StackToAdd){
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
     * @param component The input component
     * @return returns the first recipe it finds that outputs the input component
     */
    public static <T extends DefaultPCBComponent> List<Ingredient> GetComponentRecipe(T component){
        List<PCBComponentRecipe> ComponentRecipes = new ArrayList<>(Minecraft.getInstance().world.getRecipeManager().getRecipesForType(PCBRecipeTypes.PCB_COMPONENT_RECIPE));
        for (PCBComponentRecipe recipe : ComponentRecipes) {
            if (recipe.getComponentResult() == component) {
                return recipe.getIngredients();
            }
        }
        return null;
    }
    /**
     * @param wire The input wire type
     * @return returns the first recipe it finds that outputs the input component
     */
    public static <T extends VarType> List<Ingredient> GetWireRecipe(T wire){
        List<PCBWireRecipe> WireRecipes = new ArrayList<>(Minecraft.getInstance().world.getRecipeManager().getRecipesForType(PCBRecipeTypes.PCB_WIRE_RECIPE));
        for (PCBWireRecipe recipe : WireRecipes) {
            if (recipe.getWireResult() == wire) {
                return recipe.getIngredients();
            }
        }
        return null;
    }

    /**
     * Presumes that the stack1 and stack2 are the same array size and hold the same item types. Make sure you check before running these
     * @param stack1 ItemStack[]
     * @param stack2 ItemStack[]
     * @return Returns an itemstack[] the size of how much stack1 + stack2 is over there max stack size.
     */
    public static ItemStack[] GetItemStackRemainder(ItemStack[] stack1, ItemStack[] stack2){
        for (ItemStack itemStack : stack1) {
            itemStack.setCount(GetIntRemainder(stack1, stack2));
        }
        return stack1;
    }


    /**
     * Presumes that the stack1 and stack2 are the same array size and hold the same item types. - Make sure you check before running this
     * @param stack1 ItemStack[]
     * @param stack2 ItemStack[]
     * @return returns an itemstack[] of stack1 + stack2. if its higher than the max stack size then it is set to the max stack size
     */
    public static ItemStack[] AddItemStacks(ItemStack[] stack1, ItemStack[] stack2){
        ItemStack[] newStack = new ItemStack[stack1.length];
        for (int i = 0; i < stack1.length; i++) {
            newStack[i] = new ItemStack(stack1[i].getItem(),Math.min(stack1[i].getCount() + stack2[i].getCount(), stack1[i].getMaxStackSize()));
        }
        return newStack;
    }

    /**
     * Presumes that the stack1 and stack2 are the same array size and hold the same item types. - Make sure you check before running this
     * @param stack1 ItemStack[]
     * @param stack2 ItemStack[]
     * @return Math.max((stack1[0].getCount() + stack2[0].getCount()) - stack1[0].getMaxStackSize(),0)
     */
    public static int GetIntRemainder(ItemStack[] stack1, ItemStack[] stack2){
        return Math.max((stack1[0].getCount() + stack2[0].getCount()) - stack1[0].getMaxStackSize(),0);
    }

    /**
     * @param stack1 ItemStack[]
     * @param stack2 ItemStack[]
     * @return returns true if two ItemStack arrays are the same length and contain the same item types.
     */
    public static boolean DoStackArraysMatch(ItemStack[] stack1, ItemStack[] stack2){
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
}

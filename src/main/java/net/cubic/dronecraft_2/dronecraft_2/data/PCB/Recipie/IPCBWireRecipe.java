package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie;


import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IPCBWireRecipe extends IRecipe<IInventory> {
    ResourceLocation TYPE_ID = new ResourceLocation(dronecraft_2Main.MOD_ID, "pcb_wire_recipe");

    @Override
    default IRecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }

    @Override
    default boolean canFit(int width, int height) {
        return true;
    }

    @Override
    default boolean isDynamic(){
        return true;
    }
}

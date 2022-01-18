package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.VarTypes.VarType;
import net.cubic.dronecraft_2.dronecraft_2.item.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class PCBWireRecipe implements IPCBWireRecipe {

    private final ResourceLocation id;
    private final VarType wireOutput;
    private final NonNullList<Ingredient> recipeItems;

    public PCBWireRecipe(ResourceLocation id, VarType wireOutput,
                         NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.wireOutput = wireOutput;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) { //this code is to check if the ingredients are correct
        int allCorrect = 0;
        for (Ingredient recipeItem : recipeItems) {
            boolean foundItem = false;
            for (int j = 0; j < inv.getSizeInventory(); j++) {
                if (recipeItem.test(inv.getStackInSlot(j))) {
                    foundItem = true;
                }
            }
            if (foundItem) {
                allCorrect++;
            }
        }
        return allCorrect == recipeItems.size();
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ModItems.DUMMY_PCB_COMPONENT.get().getDefaultInstance();//.setDisplayName(wireOutput.getName());
    }
    public VarType getWireResult() {
        return wireOutput;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ModItems.DUMMY_PCB_COMPONENT.get().getDefaultInstance();//.setDisplayName(wireOutput.getName());
    }

    public ItemStack getIcon() {
        return new ItemStack(ModItems.DUMMY_PCB_COMPONENT.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return PCBRecipeTypes.PCB_WIRE_RECIPE_SERIALIZER.get();
    }

    public static class WireRecipeType implements IRecipeType<PCBWireRecipe> {
        @Override
        public String toString() {
            return PCBWireRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<PCBWireRecipe> {

        @Override
        public PCBWireRecipe read(ResourceLocation recipeId, JsonObject json) {
            VarType wireOutput = GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(JSONUtils.getString(json, "pcb_wire")));
            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");
            final NonNullList<Ingredient> input = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                input.set(i, Ingredient.deserialize(ingredients.get(i)));
            }
            return new PCBWireRecipe(recipeId,wireOutput,
                    input);
        }

        @Nullable
        @Override
        public PCBWireRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> input = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < input.size(); i++) {
                input.set(i, Ingredient.read(buffer));
            }
            VarType wireOutput = GameRegistry.findRegistry(VarType.class).getValue(new ResourceLocation(buffer.readString()));
            return new PCBWireRecipe(recipeId,wireOutput,
                    input);
        }

        @Override
        public void write(PacketBuffer buffer, PCBWireRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buffer);
            }
            buffer.writeString(recipe.wireOutput.getRegistryName().toString());
        }
    }
}

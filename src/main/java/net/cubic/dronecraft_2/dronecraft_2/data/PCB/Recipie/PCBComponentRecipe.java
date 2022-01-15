package net.cubic.dronecraft_2.dronecraft_2.data.PCB.Recipie;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.cubic.dronecraft_2.dronecraft_2.data.PCB.Components.PCBComponent;
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
import java.util.Collections;

public class PCBComponentRecipe implements IPCBComponentRecipe {

    private final ResourceLocation id;
    private final PCBComponent componentOutput;
    private final NonNullList<Ingredient> recipeItems;

    public PCBComponentRecipe(ResourceLocation id, PCBComponent componentOutput,
                              NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.componentOutput = componentOutput;
        this.recipeItems = recipeItems;
    }

    /*
            List<PCBComponentRecipe> recipes = minecraft.world.getRecipeManager()
                .getRecipes(PCBRecipeTypes.PCB_COMPONENT_RECIPE,inv, world);

      THIS will get all recipes that can be made with the items in the inv



     */


    @Override
    public boolean matches(IInventory inv, World worldIn) { //this code is to check if the ingredients are correct, this recipe is shapeless so i just loop though and check if the items exist;
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
        return ModItems.DUMMY_PCB_COMPONENT.get().getDefaultInstance().setDisplayName(componentOutput.getName());
    }
    public PCBComponent getComponentResult() {
        return componentOutput;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ModItems.DUMMY_PCB_COMPONENT.get().getDefaultInstance().setDisplayName(componentOutput.getName());
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
        return PCBRecipeTypes.PCB_COMPONENT_RECIPE_SERIALIZER.get();
    }

    public static class ComponentRecipeType implements IRecipeType<PCBComponentRecipe> {
        @Override
        public String toString() {
            return PCBComponentRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<PCBComponentRecipe> {

        @Override
        public PCBComponentRecipe read(ResourceLocation recipeId, JsonObject json) {
            PCBComponent componentOutput = GameRegistry.findRegistry(PCBComponent.class).getValue(new ResourceLocation(JSONUtils.getString(json, "pcb_component")));
            JsonArray ingredients = JSONUtils.getJsonArray(json, "ingredients");



            final NonNullList<Ingredient> input = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                input.set(i, Ingredient.deserialize(ingredients.get(i)));
            }


            return new PCBComponentRecipe(recipeId,componentOutput,
                    input);
        }

        @Nullable
        @Override
        public PCBComponentRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> input = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < input.size(); i++) {
                input.set(i, Ingredient.read(buffer));
            }

            PCBComponent componentOutput = GameRegistry.findRegistry(PCBComponent.class).getValue(new ResourceLocation(buffer.readString()));
            return new PCBComponentRecipe(recipeId,componentOutput,
                    input);
        }

        @Override
        public void write(PacketBuffer buffer, PCBComponentRecipe recipe) {

            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buffer);
            }

            buffer.writeString(recipe.componentOutput.getRegistryName().toString());
        }
    }
}

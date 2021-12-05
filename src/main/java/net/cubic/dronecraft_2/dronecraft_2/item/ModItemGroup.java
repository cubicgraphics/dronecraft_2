package net.cubic.dronecraft_2.dronecraft_2.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {


    public static final ItemGroup DRONECRAFT2_GROUP = new ItemGroup("dronecraft_2tab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.CARBON_CONDUCTIUM_INGOT.get());
        }
    };
}

package net.cubic.dronecraft_2.dronecraft_2.Utill;

import net.cubic.dronecraft_2.dronecraft_2.dronecraft_2Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class Tags {

    public static class Blocks {

        // example block tag
        //public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Block> FIRESTONE_CLICKABLE_BLOCKS =
        //        createTag("firestone_clickable_blocks");

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(dronecraft_2Main.MOD_ID, name));
        }

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }

    public static class Items {

    }
        public static final net.minecraftforge.common.Tags.IOptionalNamedTag<Item> CONDUCTIUM_INGOT = createForgeTag("ingots/conductium");

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> createTag(String name) {
            return ItemTags.createOptional(new ResourceLocation(dronecraft_2Main.MOD_ID, name));
        }

        private static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> createForgeTag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
}

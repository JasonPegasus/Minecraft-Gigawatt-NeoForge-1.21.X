package com.jasonpegasus.gigawatt;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class GwTags {
    public static class Blocks {
        private static TagKey<Block> createTag(String name)
        {return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Gigawatt.MOD_ID, name));}
    }

    public static class Items{
        public static final TagKey<Item> DUST_ITEMS = createTag("dusts", true);

        private static TagKey<Item> createTag(String name, boolean common)
        {return ItemTags.create(ResourceLocation.fromNamespaceAndPath(common ? "c" : Gigawatt.MOD_ID, name));}
    }
}

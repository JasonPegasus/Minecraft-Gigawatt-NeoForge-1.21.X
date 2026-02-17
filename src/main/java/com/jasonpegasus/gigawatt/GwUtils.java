package com.jasonpegasus.gigawatt;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.util.List;
import java.util.Random;

public class GwUtils {
    public static int K = 1000;
    public static int M = K*1000;
    public static int G = M*1000;
    public static int T = G*1000;

    public static void print(String msg)
    { System.out.println(msg); }

    public static float random(float min, float max)
    { return (float) (min + Math.random() * (max - min)); }

    public static int randomInt(int min, int max)
    {
        return new Random().ints(min, max)
                .findFirst()
                .getAsInt();
    }

    public static Direction getBlockFacing(BlockState state)
    { return state.getValue(DirectionalBlock.FACING); }

    public static void serverStart(ServerStartedEvent event)
    {
        getWoodTypes();
    }

    // WOOD TYPES STUFF //
    private static List<String> WOOD_TYPES;
    public static List<String> getWoodTypes()
    {
        if (WOOD_TYPES == null)
        {
            WOOD_TYPES = BuiltInRegistries.BLOCK.getTag(BlockTags.PLANKS).orElseThrow().stream().map(holder -> {
                        Block block = holder.value();
                        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
                        return id.getPath().replace("_planks", "");
                    })
                    .toList();
        }
        return WOOD_TYPES;
    }

    // BLOCKSTATE COPYING //

    public static boolean isBlockIDValid(String id)
    { return BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(id)) != Blocks.AIR; }

    public static BlockState copyProperties(BlockState from, BlockState to)
    {
        for (Property<?> property : from.getProperties()) {
            if (to.hasProperty(property)) {
                to = copySpecificProperty(from, to, property);
            }
        }
        return to;
    }

    private static <T extends Comparable<T>> BlockState copySpecificProperty( BlockState from, BlockState to, Property<T> property)
    { return to.setValue(property, from.getValue(property)); }
}


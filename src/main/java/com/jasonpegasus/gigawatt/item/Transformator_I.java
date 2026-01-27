package com.jasonpegasus.gigawatt.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class Transformator_I extends Item {
    public Transformator_I(Properties properties) {
        super(properties);
    }

    private static final Block[] trans_Planks = {
            Blocks.OAK_PLANKS,
            Blocks.SPRUCE_PLANKS,
            Blocks.DARK_OAK_PLANKS,
            Blocks.BIRCH_PLANKS,
            Blocks.ACACIA_PLANKS,
            Blocks.JUNGLE_PLANKS,
            Blocks.CHERRY_PLANKS,
            Blocks.MANGROVE_PLANKS,
            Blocks.CRIMSON_PLANKS,
            Blocks.WARPED_PLANKS,
    };

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block block = level.getBlockState(context.getClickedPos()).getBlock();
        int i = 0;
        for (Block b : trans_Planks)
        {
            if (!level.isClientSide() && b == block)
            {
                level.setBlockAndUpdate(context.getClickedPos(), trans_Planks[i<trans_Planks.length-1 ? i+1 : 0].defaultBlockState());
                level.playSound(null, context.getClickedPos(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 0.5f, (float)(0.75 + Math.random() * (1.25 - 0.75)));
                return InteractionResult.SUCCESS;
            }
            i++;
        }

        return InteractionResult.FAIL;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    {return UseAnim.BRUSH;}
}

package com.jasonpegasus.gigawatt.item;

import com.jasonpegasus.gigawatt.GwUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class WandOfTransmutation_I extends Item {

    public WandOfTransmutation_I(Properties properties) {
        super(properties);
    }



    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
            BlockState blockState = level.getBlockState(context.getClickedPos());
            Block block = blockState.getBlock();

            String blockId = BuiltInRegistries.BLOCK.getKey(block).toString();
            GwUtils.print("Original block ID is: " + blockId);

            String blockWoodType = getMatchingWoodType(blockId);
            if (blockWoodType == null) {
                GwUtils.print("Matching WoodType not found!");
                return InteractionResult.FAIL;
            }

            String newBlockId = blockId.replace(blockWoodType, getNextWoodType(blockWoodType));
            GwUtils.print("New block ID is: " + newBlockId);

            ResourceLocation rl = ResourceLocation.tryParse(newBlockId);
            if (BuiltInRegistries.BLOCK.get(rl) == Blocks.AIR) {
                GwUtils.print("The new block ID haven't been found!");
                return InteractionResult.FAIL;
            }

        if (!level.isClientSide) {
            BlockState newBlockState = BuiltInRegistries.BLOCK.get(rl).defaultBlockState();
            newBlockState = GwUtils.copyProperties(blockState, newBlockState);

            level.setBlockAndUpdate(context.getClickedPos(), newBlockState);
        }

        return InteractionResult.SUCCESS;
    }

    private String getNextWoodType(String woodType)
    {
        int i = GwUtils.getWoodTypes().indexOf(woodType)+1;
        if (GwUtils.getWoodTypes().size()-1 < i ){ i = 0;}
        return GwUtils.getWoodTypes().get(i);
    }

    private String getMatchingWoodType(String id)
    {
        String bestMatch = null;
        for (String w : GwUtils.getWoodTypes()) {
            if (id.contains(w)) {
                if (bestMatch == null || w.length() > bestMatch.length()) {
                    bestMatch = w;
                }
            }
        }
        return bestMatch;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack)
    { return UseAnim.BRUSH; }
}
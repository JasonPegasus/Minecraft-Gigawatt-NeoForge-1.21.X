package com.jasonpegasus.gigawatt.item;

import com.jasonpegasus.gigawatt.GwUtils;
import com.jasonpegasus.gigawatt.item.renderer.WandOfTransmutationRenderer;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

import static org.joml.Math.lerp;

public class WandOfTransmutationItem extends Item {

    public WandOfTransmutationItem() {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON)
        );
    }



    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) { return InteractionResult.PASS; }

        BlockState blockState = level.getBlockState(context.getClickedPos());
        Block block = blockState.getBlock();

        String blockId = BuiltInRegistries.BLOCK.getKey(block).toString();
        GwUtils.print("Original block ID is: " + blockId);

        String blockWoodType = getMatchingWoodType(blockId);
        if (blockWoodType == null) {
            GwUtils.print("Matching WoodType not found!");
            return InteractionResult.FAIL;
        }

        String newBlockId = "";
        int i = 1;
        while (!GwUtils.isBlockIDValid(newBlockId))
        {
            newBlockId = blockId.replace(blockWoodType, getNextWoodTypeBy(blockWoodType, i));
            i++;
            if (i > GwUtils.getWoodTypes().size()*2)
            {
                GwUtils.print("Failed iterating until finding new block");
                return InteractionResult.FAIL;
            }
        }
        GwUtils.print("New block ID is: " + newBlockId);

        level.playSound(null, context.getClickedPos(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1f, (float)lerp(1.5, 2, Math.random()));

        BlockState newBlockState = BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse(newBlockId)).defaultBlockState();
        newBlockState = GwUtils.copyProperties(blockState, newBlockState);

        level.setBlockAndUpdate(context.getClickedPos(), newBlockState);

        return InteractionResult.SUCCESS;
    }

    private String getNextWoodTypeBy(String woodType, int iteration)
    {
        int i = GwUtils.getWoodTypes().indexOf(woodType)+iteration;
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
    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("removal")
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new WandOfTransmutationRenderer()));
    }
}
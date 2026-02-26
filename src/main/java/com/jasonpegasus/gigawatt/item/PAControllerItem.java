package com.jasonpegasus.gigawatt.item;

import com.jasonpegasus.gigawatt.GwBlocksCreate;
import com.jasonpegasus.gigawatt.GwDataComponents;
import com.jasonpegasus.gigawatt.GwUtils;
import com.jasonpegasus.gigawatt.blockentity.PAControllerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class PAControllerItem extends BlockItem {
    public PAControllerItem(Block block, Properties properties) {
        super(block, properties.stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    private List<BlockPos> getPositions(ItemStack item){
        if (!item.is(this)) {
            GwUtils.print("Attemped to get module pos with an item that isn't correct!");
            return null;
        }

        List<BlockPos> og = item.get(GwDataComponents.BLOCK_POSITION_LIST);
        if (og == null) {
            return resetPositions(item);
        }
        return new ArrayList<>(og);
    }

    private boolean addPosition(ItemStack item, BlockPos newPos) {
        List<BlockPos> list = getPositions(item);
        if (!(list == null) && !list.contains(newPos)) {
            list.add(newPos);
            item.set(GwDataComponents.BLOCK_POSITION_LIST, list);
            return true;
        }
        return false;
    }

    private List<BlockPos> resetPositions(ItemStack item)
    { return item.set(GwDataComponents.BLOCK_POSITION_LIST, new ArrayList<>()); }

    @Override
    public InteractionResult useOn(UseOnContext c) {
        Player player = c.getPlayer();

        if (!c.getItemInHand().is(this) || player.isSteppingCarefully() || !c.getLevel().getBlockState(c.getClickedPos()).is(GwBlocksCreate.PARTICLE_ACCELERATOR_MODULE))
            return super.useOn(c);

        if (addPosition(c.getItemInHand(), c.getClickedPos())) {
            c.getLevel().playSound(player, player.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1, 1.3f);
            player.displayClientMessage(Component.translatable("message.particle_accelerator.addmodule")
                    .append(c.getClickedPos().toShortString())
                    .withStyle(ChatFormatting.GREEN), true);
        }
        else{
            c.getLevel().playSound(player, player.blockPosition(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1, 0.5f);
            player.displayClientMessage(Component.translatable("message.particle_accelerator.alreadypresentmodule").withStyle(ChatFormatting.RED), true);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (player.isSteppingCarefully()){
            resetPositions(player.getItemInHand(usedHand));
            level.playSound(player, player.blockPosition(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1, 0.5f);
            player.displayClientMessage(Component.translatable("message.particle_accelerator.clearmodules").withStyle(ChatFormatting.RED), true);
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        List<BlockPos> posList = stack.get(GwDataComponents.BLOCK_POSITION_LIST);
        if (posList != null){
            int i = 0;
            for(BlockPos pos : posList){
                i++;
                tooltipComponents.add(Component.literal("Mod. "+ i + ": [" + pos.toShortString() + "]").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext c, BlockState state) {
        var positions = getPositions(c.getItemInHand());

        boolean ret = super.placeBlock(c, state);
        if (!ret) return false;

        PAControllerBlockEntity be = (PAControllerBlockEntity) c.getLevel().getBlockEntity(c.getClickedPos());
        if (be != null && positions != null) {
            for(BlockPos pos : positions){
                be.addModule(pos);
            }
        }

        return true;
    }
}

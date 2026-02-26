package com.jasonpegasus.gigawatt.block;

import com.jasonpegasus.gigawatt.GwBlockEntitiesCreate;
import com.jasonpegasus.gigawatt.blockentity.PAModuleBlockEntity;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PAModuleBlock extends Block implements IWrenchable, IBE<PAModuleBlockEntity>, ProperWaterloggedBlock {
    public PAModuleBlock(Properties properties) {
        super(properties);
    }

    static PAModuleBlockEntity blockEntity(Level level, BlockPos pos) { return (PAModuleBlockEntity) level.getBlockEntity(pos); }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    { return this.defaultBlockState().setValue(DirectionalBlock.FACING, context.getHorizontalDirection().getOpposite()); }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    { builder.add(DirectionalBlock.FACING); }

    @Override public Class<PAModuleBlockEntity> getBlockEntityClass() { return PAModuleBlockEntity.class; }
    @Override public BlockEntityType<? extends PAModuleBlockEntity> getBlockEntityType() { return GwBlockEntitiesCreate.PARTICLE_ACCELERATOR_MODULE.get(); }
}
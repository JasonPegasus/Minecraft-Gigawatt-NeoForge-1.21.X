package com.jasonpegasus.gigawatt.block;

import com.jasonpegasus.gigawatt.GwBlockEntitiesCreate;
import com.jasonpegasus.gigawatt.blockentity.PAControllerBlockEntity;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

public class PAControllerBlock extends Block implements IWrenchable, IBE<PAControllerBlockEntity>, ProperWaterloggedBlock {
    public PAControllerBlock(Properties properties) {
        super(properties);
    }

    static PAControllerBlockEntity blockEntity(Level level, BlockPos pos) { return (PAControllerBlockEntity) level.getBlockEntity(pos); }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.AIR)){
            blockEntity(level, pos).active = !blockEntity(level, pos).active;
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    { return this.defaultBlockState().setValue(DirectionalBlock.FACING, context.getHorizontalDirection().getOpposite()); }

    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    { builder.add(DirectionalBlock.FACING); }

    @Override public Class<PAControllerBlockEntity> getBlockEntityClass() { return PAControllerBlockEntity.class; }
    @Override public BlockEntityType<? extends PAControllerBlockEntity> getBlockEntityType() { return GwBlockEntitiesCreate.PARTICLE_ACCELERATOR_CONTROLLER.get(); }
}
package com.jasonpegasus.gigawatt.block;

import com.jasonpegasus.gigawatt.GwBlockEntitiesCreate;
import com.jasonpegasus.gigawatt.blockentity.RepairStationBlockEntity;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllShapes;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RepairStationBlock extends Block implements IWrenchable, IBE<RepairStationBlockEntity>, ProperWaterloggedBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public RepairStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) { return SHAPE; }


    @Override
    public Class<RepairStationBlockEntity> getBlockEntityClass() {
        return RepairStationBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends RepairStationBlockEntity> getBlockEntityType() {
        return GwBlockEntitiesCreate.REPAIR_STATION.get();
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock())
        {
            if (level.getBlockEntity(pos) instanceof  RepairStationBlockEntity be)
            {
                be.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof  RepairStationBlockEntity be)
        {
            if (be.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty())
            {
                be.inventory.insertItem(0, stack.copy(), false);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1, 2);
            }
            else if (stack.isEmpty())
            {
                ItemStack stackIn = be.inventory.extractItem(0, 1, false);
                player.setItemInHand(InteractionHand.MAIN_HAND, stackIn);
                be.clearContent();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1, 1);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override // selecciona el facing dependiendo de como se pone
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {return this.defaultBlockState().setValue(DirectionalBlock.FACING, context.getHorizontalDirection().getOpposite());}

    @Override // aplica el cambio en el facing
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {builder.add(DirectionalBlock.FACING);}
}

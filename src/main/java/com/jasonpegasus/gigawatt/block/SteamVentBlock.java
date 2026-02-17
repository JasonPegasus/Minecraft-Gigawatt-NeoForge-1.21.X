package com.jasonpegasus.gigawatt.block;

import com.jasonpegasus.gigawatt.GwBlockEntitiesCreate;
import com.jasonpegasus.gigawatt.GwUtils;
import com.jasonpegasus.gigawatt.blockentity.RepairStationBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.SteamVentBlockEntity;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.fluids.pump.PumpBlock;
import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import static com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock.getPreferredAxis;
import static net.minecraft.world.level.block.RotatedPillarBlock.AXIS;

public class SteamVentBlock extends Block implements IWrenchable, IBE<SteamVentBlockEntity> {
    public SteamVentBlock(Properties properties) {
        super(properties);
    }


    @Override
    public Class<SteamVentBlockEntity> getBlockEntityClass() {
        return SteamVentBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SteamVentBlockEntity> getBlockEntityType() {
        return GwBlockEntitiesCreate.STEAM_VENT.get();
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == GwUtils.getBlockFacing(state).getOpposite()) {
            ((SteamVentBlockEntity) level.getBlockEntity(pos)).findPump();
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override // selecciona el facing dependiendo de como se pone
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {return this.defaultBlockState().setValue(DirectionalBlock.FACING, context.getNearestLookingDirection().getOpposite());}

    @Override // aplica el cambio en el facing
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    { builder.add(DirectionalBlock.FACING); }

}

package com.jasonpegasus.gigawatt.block;

import com.jasonpegasus.gigawatt.GwInfiniteEnergy;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.energy.EnergyStorage;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Console;
import java.io.PrintStream;

public class Battery_B extends BaseEntityBlock {


    private static final Logger log = LoggerFactory.getLogger(Battery_B.class);

    public Battery_B(Properties p) {
        super(p);
    }



    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof Battery_BE battery_be)
        {
            GwInfiniteEnergy energy = (GwInfiniteEnergy) battery_be.getEnergy();
            System.out.println("ENERGY: "+energy.getEnergyStored()+"/"+energy.getMaxEnergyStored());
            System.out.println("REAL ENERGY: "+energy.getRealEnergyStored());
        }
        return InteractionResult.SUCCESS;
    }

    // BLOCK ENTITY STUFF //

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {return new Battery_BE(pos, state);}

    // DIRECTIONAL BLOCK //

    public static final MapCodec<Battery_B> CODEC = simpleCodec(Battery_B::new);

    @Override //codec no se para que
    protected MapCodec<? extends BaseEntityBlock> codec()
    {return CODEC;}

    @Override // selecciona el facing dependiendo de como se pone
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context)
    {return this.defaultBlockState().setValue(DirectionalBlock.FACING, context.getHorizontalDirection().getOpposite());}

    @Override // aplica el cambio en el facing
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {builder.add(DirectionalBlock.FACING);}
}

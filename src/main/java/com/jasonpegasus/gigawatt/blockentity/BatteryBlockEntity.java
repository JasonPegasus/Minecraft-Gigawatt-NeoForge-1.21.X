package com.jasonpegasus.gigawatt.blockentity;

import com.jasonpegasus.gigawatt.GwBlockEntities;
import com.jasonpegasus.gigawatt.GwInfiniteEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public class BatteryBlockEntity extends BlockEntity {

    public final EnergyStorage energy = (EnergyStorage) new GwInfiniteEnergy(GwInfiniteEnergy.M*2, GwInfiniteEnergy.MAX);

    public EnergyStorage getEnergy() { return energy; }

    public BatteryBlockEntity(BlockPos pos, BlockState blockState) { super(GwBlockEntities.BATTERY_BE.get(), pos, blockState); }

}
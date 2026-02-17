package com.jasonpegasus.gigawatt.blockentity;

import com.jasonpegasus.gigawatt.GwBlockEntities;
import com.jasonpegasus.gigawatt.custom.GwInfiniteEnergyCapacity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public class BatteryBlockEntity extends BlockEntity {

    public final EnergyStorage energy = (EnergyStorage) new GwInfiniteEnergyCapacity(GwInfiniteEnergyCapacity.M*2, GwInfiniteEnergyCapacity.MAX);

    public EnergyStorage getEnergy() { return energy; }

    public BatteryBlockEntity(BlockPos pos, BlockState blockState) { super(GwBlockEntities.BATTERY_BE.get(), pos, blockState); }



}
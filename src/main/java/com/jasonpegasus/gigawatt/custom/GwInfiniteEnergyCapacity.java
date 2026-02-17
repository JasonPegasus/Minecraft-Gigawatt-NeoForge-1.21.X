package com.jasonpegasus.gigawatt.custom;

import net.neoforged.neoforge.energy.EnergyStorage;

public class GwInfiniteEnergyCapacity extends EnergyStorage {
    public static final int K = 1000;
    public static final int M = K*1000;
    public static final int G = M*1000;
    public static final int MAX = Integer.MAX_VALUE;

    private long energy = 0;

    @Override
    public int getEnergyStored() { return this.getEnergyAsInt(); }

    public long getRealEnergyStored() { return this.energy; }

    public GwInfiniteEnergyCapacity(int startCapacity, int maxTransfer)
    { super(startCapacity, maxTransfer, maxTransfer); }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate)
    {
        if (!canReceive() || toReceive <= 0) { return 0; }
        int receivedEnergy = Math.min(this.maxReceive, toReceive);

        if (!simulate) {
            energy += receivedEnergy;
            updateCapacity();
        }
        return receivedEnergy;
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        if (!canExtract() || toExtract <= 0 ) {
            return 0;
        }

        int energyExtracted = Math.min(getEnergyAsInt(), Math.min(this.maxExtract, toExtract));
        if (!simulate)
        {
            this.energy -= energyExtracted;
            updateCapacity();
        }
        return energyExtracted;
    }

    private int getEnergyAsInt()
    { return longToInt(energy); }

    private int longToInt(long l)
    { return (int) Math.clamp((long) l, 0, MAX); }

    public void updateCapacity(){ capacity = getEnergyAsInt(); }
}
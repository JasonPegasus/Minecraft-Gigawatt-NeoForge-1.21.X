package com.jasonpegasus.gigawatt.custom;

import net.neoforged.neoforge.energy.EnergyStorage;

public class GwEnergy extends EnergyStorage {
    public GwEnergy(int capacity) {
        super(capacity, capacity, capacity, 0);
    }

    public GwEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public void setEnergy(int energySet)
    { this.energy = energySet; }

    public void addEnergy(int energyAdd)
    { this.energy += energyAdd; }
}

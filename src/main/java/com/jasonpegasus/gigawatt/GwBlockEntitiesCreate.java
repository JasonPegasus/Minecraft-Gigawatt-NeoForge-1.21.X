package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.blockentity.ParticleAcceleratorBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.RepairStationBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.SteamVentBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.renderer.RepairStationRenderer;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class GwBlockEntitiesCreate {
    private static final CreateRegistrate REGISTRATE = Gigawatt.registrate();

    public static final BlockEntityEntry<RepairStationBlockEntity> REPAIR_STATION = REGISTRATE
            .blockEntity("repair_station_be", RepairStationBlockEntity::new)
            .validBlocks(GwBlocksCreate.REPAIR_STATION)
            .register();

    public static final BlockEntityEntry<SteamVentBlockEntity> STEAM_VENT = REGISTRATE
            .blockEntity("steam_vent_be", SteamVentBlockEntity::new)
            .validBlocks(GwBlocksCreate.STEAM_VENT)
            .register();

    public static final BlockEntityEntry<ParticleAcceleratorBlockEntity> PARTICLE_ACCELERATOR = REGISTRATE
            .blockEntity("particle_accelerator_be", ParticleAcceleratorBlockEntity::new)
            .validBlocks(GwBlocksCreate.PARTICLE_ACCELERATOR_CONTROLLER)
            .register();

    public static void register() {}
}

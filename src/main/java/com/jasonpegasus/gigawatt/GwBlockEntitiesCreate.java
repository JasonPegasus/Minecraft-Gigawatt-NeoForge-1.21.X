package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.blockentity.PAControllerBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.PAModuleBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.RepairStationBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.SteamVentBlockEntity;
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

    public static final BlockEntityEntry<PAControllerBlockEntity> PARTICLE_ACCELERATOR_CONTROLLER = REGISTRATE
            .blockEntity("particle_accelerator_controller_be", PAControllerBlockEntity::new)
            .validBlocks(GwBlocksCreate.PARTICLE_ACCELERATOR_CONTROLLER)
            .register();

    public static final BlockEntityEntry<PAModuleBlockEntity> PARTICLE_ACCELERATOR_MODULE = REGISTRATE
            .blockEntity("particle_accelerator_module_be", PAModuleBlockEntity::new)
            .validBlocks(GwBlocksCreate.PARTICLE_ACCELERATOR_MODULE)
            .register();

    public static void register() {}
}

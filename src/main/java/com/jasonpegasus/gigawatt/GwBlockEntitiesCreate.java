package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.blockentity.RepairStationBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class GwBlockEntitiesCreate {
    private static final CreateRegistrate REGISTRATE = Gigawatt.registrate();

    public static final BlockEntityEntry<RepairStationBlockEntity> REPAIR_STATION = REGISTRATE
            .blockEntity("repair_station_be", RepairStationBlockEntity::new)
            .register();

    public static void register() {}
}

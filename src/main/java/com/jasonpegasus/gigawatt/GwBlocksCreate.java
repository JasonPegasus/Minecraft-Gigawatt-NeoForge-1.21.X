package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.block.RepairStationBlock;
import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllMountedStorageTypes;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.logistics.depot.MountedDepotInteractionBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour.interactionBehaviour;
import static com.simibubi.create.api.contraption.storage.item.MountedItemStorageType.mountedItemStorage;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;


public class GwBlocksCreate {
    public static final CreateRegistrate REGISTRATE = Gigawatt.registrate();

    // BLOCKS //
    public static final BlockEntry<CasingBlock> HEAVY_CASING = REGISTRATE.block("heavy_casing", CasingBlock::new)
            .transform(BuilderTransformers.casing(() -> GwSpriteShifts.HEAVY_CASING))
            .properties(p -> p
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(20f)
                    .explosionResistance(500)
                    .sound(SoundType.NETHERITE_BLOCK)
                    .requiresCorrectToolForDrops()
            )
            .register();

    public static final BlockEntry<RepairStationBlock> REPAIR_STATION = REGISTRATE.block("repair_station", RepairStationBlock::new)
            .transform(axeOrPickaxe())
            .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
            .properties(p -> p
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(20)
                    .sound(SoundType.NETHERITE_BLOCK)
            )
            .register();

    public static void register() {}

}

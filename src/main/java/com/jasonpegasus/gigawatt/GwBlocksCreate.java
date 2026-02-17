package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.block.ParticleAcceleratorBlock;
import com.jasonpegasus.gigawatt.block.RepairStationBlock;
import com.jasonpegasus.gigawatt.block.SteamVentBlock;
import com.jasonpegasus.gigawatt.block.UraniumRodsBlock;
import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllMountedStorageTypes;
import com.simibubi.create.content.decoration.encasing.CasingBlock;
import com.simibubi.create.content.logistics.depot.MountedDepotInteractionBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.BuilderTransformers;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
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
            .properties(p -> p
                    .noOcclusion()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(20)
                    .explosionResistance(500)
                    .sound(SoundType.NETHERITE_BLOCK)
            )
            .item()
            .transform(customItemModel("_", "block"))
            .register();

    public static final BlockEntry<Block> URANIUM_BLOCK = REGISTRATE.block("uranium_block", Block::new)
            .properties(p -> p
                    .mapColor(MapColor.TERRACOTTA_GREEN)
                    .strength(10)
                    .explosionResistance(6)
                    .sound(SoundType.METAL)
            )
            .item()
            .transform(customItemModel("_", "block"))
            .register();

    public static final BlockEntry<UraniumRodsBlock> URANIUM_RODS_BLOCK = REGISTRATE.block("uranium_rods_block", UraniumRodsBlock::new)
            .properties(p -> p
                    .mapColor(MapColor.TERRACOTTA_LIGHT_GREEN)
                    .strength(7)
                    .explosionResistance(6)
                    .sound(SoundType.COPPER)
            )
            .item()
            .transform(customItemModel("_", "block"))
            .register();

    public static final BlockEntry<SteamVentBlock> STEAM_VENT = REGISTRATE.block("steam_vent", SteamVentBlock::new)
            .transform(axeOrPickaxe())
            .properties(p -> p
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(20)
                    .explosionResistance(500)
                    .sound(SoundType.NETHERITE_BLOCK)
            )
            .item()
            .transform(customItemModel("_", "block"))
            .register();

    public static final BlockEntry<ParticleAcceleratorBlock> PARTICLE_ACCELERATOR_CONTROLLER = REGISTRATE.block("particle_accelerator_controller", ParticleAcceleratorBlock::new)
            .transform(axeOrPickaxe())
            .properties(p -> p
                    .noOcclusion()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(20)
                    .explosionResistance(500)
                    .sound(SoundType.NETHERITE_BLOCK)
            )
            .item()
            .transform(customItemModel("_", "block"))
            .register();


    public static void register() {}

}

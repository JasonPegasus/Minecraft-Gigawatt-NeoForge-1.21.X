package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.blockentity.BatteryBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class GwBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Gigawatt.MOD_ID);

    public static final Supplier<BlockEntityType<BatteryBlockEntity>> BATTERY_BE =
            BLOCK_ENTITIES.register("battery_be", () -> BlockEntityType.Builder.of(BatteryBlockEntity::new, GwBlocks.BATTERY.get()).build(null));

    public static void register(IEventBus eventBus)
    {BLOCK_ENTITIES.register(eventBus);}
}

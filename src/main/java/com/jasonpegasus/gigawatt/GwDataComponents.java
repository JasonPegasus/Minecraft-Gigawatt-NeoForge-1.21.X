package com.jasonpegasus.gigawatt;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.UnaryOperator;

public class GwDataComponents {
    @SuppressWarnings("removal")
    public static final DeferredRegister<DataComponentType<?>> DC_REGISTERER = DeferredRegister.createDataComponents(Gigawatt.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> BLOCK_POSITION =
            newDC("blockpos", b->b.persistent(BlockPos.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<BlockPos>>> BLOCK_POSITION_LIST =
            newDC("blockposlist", b->b.persistent(BlockPos.CODEC.listOf()));

    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> newDC(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator)
    { return DC_REGISTERER.register(name, () -> builderOperator.apply(DataComponentType.builder()).build()); }

    public static void register(IEventBus e) { DC_REGISTERER.register(e); }
}

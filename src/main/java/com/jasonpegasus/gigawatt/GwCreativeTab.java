package com.jasonpegasus.gigawatt;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class GwCreativeTab {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Gigawatt.MOD_ID);

    public static final Supplier<CreativeModeTab> GIGAWATT_ITEMS_TAB = CREATIVE_MODE_TAB.register("gigawatt_items_tab",
            ()-> CreativeModeTab.builder()
                    .icon(()-> new ItemStack(GwItems.NETHER_STAR_DUST.get()))
                    .title(Component.translatable("creative_tab.gigawatt.gigawatt_items"))
                    .displayItems(acceptAllItems())
                    .build()
    );

    public static CreativeModeTab.DisplayItemsGenerator acceptAllItems()
    {
        return new CreativeModeTab.DisplayItemsGenerator() {
            @Override
            public void accept(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
                for (var i : GwItems.ITEMS.getEntries())
                {
                    output.accept(i.get());
                }
                for (var i : Gigawatt.registrate().getAll(Registries.ITEM))
                {
                    output.accept(i.get());
                }
            }
        };
    }

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}

package com.jasonpegasus.gigawatt;

import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.CreativeModeTab;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Gigawatt.MOD_ID)
public class Gigawatt {

    public static final String MOD_ID = "gigawatt";
    public static final Logger LOGGER = LogUtils.getLogger();


    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    public Gigawatt(IEventBus modEventBus, ModContainer modContainer) {
        REGISTRATE.registerEventListeners(modEventBus);

        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);
        GwCreativeTab.register(modEventBus);
        GwEffects.register(modEventBus);
        GwItems.register(modEventBus);
        GwBlocks.register(modEventBus);
        GwBlockEntities.register(modEventBus);
        GwBlocksCreate.register();
        GwBlockEntitiesCreate.register();
        GwFluids.register();
        GwDataComponents.register(modEventBus);
        GwModels.register();


        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, GigawattConfig.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    public static ResourceLocation asResource(String path) { return ResourceLocation.fromNamespaceAndPath(MOD_ID, path); }

    public static CreateRegistrate registrate() { return REGISTRATE; }

}
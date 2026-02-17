package com.jasonpegasus.gigawatt;


import com.jasonpegasus.gigawatt.block.ParticleAcceleratorBlock;
import com.jasonpegasus.gigawatt.blockentity.BatteryBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.ParticleAcceleratorBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.RepairStationBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.SteamVentBlockEntity;
import com.jasonpegasus.gigawatt.blockentity.renderer.ParticleAcceleratorRenderer;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber(modid = Gigawatt.MOD_ID)
public class GwEvents {

//    @SubscribeEvent
//    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
//        WandOfTransmutation_I.registerClientExtensions(event);
//    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent e) {
        e.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                GwBlockEntities.BATTERY_BE.get(),
                (BatteryBlockEntity be, Direction side) -> be.getEnergy()
        );
        RepairStationBlockEntity.registerCapabilities(e);
        SteamVentBlockEntity.registerCapabilities(e);
        ParticleAcceleratorBlockEntity.registerCapabilities(e);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers e) {
        RepairStationBlockEntity.registerRenderers(e);
        ParticleAcceleratorBlockEntity.registerRenderers(e);
    }


    @SubscribeEvent
    public static void registerTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        String namespace = BuiltInRegistries.ITEM.getKey(item).getNamespace();
        if (!namespace.equals(Gigawatt.MOD_ID)) { return; }

        TooltipModifier descriptionModifier = new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE);
        TooltipModifier kineticModifier = TooltipModifier.mapNull(KineticStats.create(item));
        TooltipModifier finalModifier = descriptionModifier.andThen(kineticModifier);
        finalModifier.modify(event);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event)
    {
        GwUtils.serverStart(event);
    }

}

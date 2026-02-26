package com.jasonpegasus.gigawatt.blockentity;

import com.jasonpegasus.gigawatt.*;
import com.jasonpegasus.gigawatt.blockentity.renderer.ItemStackRenderer;
import com.jasonpegasus.gigawatt.custom.GwEnergy;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class PAModuleBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation{
    public PAModuleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    ///////////////////////////////////////// CAPABILITIES ////////////////////////////////////////////

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                GwBlockEntitiesCreate.PARTICLE_ACCELERATOR_MODULE.get(),
                (PAModuleBlockEntity be, Direction side) -> be.inventory
        );
    }

    public final GwEnergy energy = new GwEnergy(2*GwUtils.M);

    public final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide)
            { level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3); }
            sendData();
            super.onContentsChanged(slot);
        }
    };

    //////////////////////////////////////////// BEHAVIOR ////////////////////////////////////////////

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.brass_tunnel.contains").style(ChatFormatting.WHITE).forGoggles(tooltip);
        CreateLang.translate("tooltip.brass_tunnel.contains_entry",
                Component.translatable(inventory.getStackInSlot(0).getDescriptionId()).getString(),
                inventory.getStackInSlot(0).getCount()).style(ChatFormatting.GRAY).forGoggles(tooltip);
        return true;
    }

    //////////////////////////////////////////// REGISTRATIONS ////////////////////////////////////////////

//    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(GwBlockEntitiesCreate.PARTICLE_ACCELERATOR_CONTROLLER.get(),
//                context -> new ItemStackRenderer(context, GwBlockEntitiesCreate.PARTICLE_ACCELERATOR_MODULE));
//    }


    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }
}
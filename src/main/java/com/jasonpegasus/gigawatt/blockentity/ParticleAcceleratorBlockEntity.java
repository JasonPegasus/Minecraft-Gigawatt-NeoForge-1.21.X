package com.jasonpegasus.gigawatt.blockentity;

import com.jasonpegasus.gigawatt.GwBlockEntitiesCreate;
import com.jasonpegasus.gigawatt.GwUtils;
import com.jasonpegasus.gigawatt.blockentity.renderer.ParticleAcceleratorRenderer;
import com.jasonpegasus.gigawatt.blockentity.renderer.RepairStationRenderer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class ParticleAcceleratorBlockEntity extends SmartBlockEntity {
    public ParticleAcceleratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            GwUtils.print(((level.isClientSide ? "[CLIENT]: " : "[SERVER]: ") + "Content Changed!"));
            if (!level.isClientSide)
            { level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3); }
            sendData();
            super.onContentsChanged(slot);
        }
    };

    public boolean active = false;




    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                GwBlockEntitiesCreate.PARTICLE_ACCELERATOR.get(),
                (ParticleAcceleratorBlockEntity be, Direction side) -> be.inventory
        );
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    { event.registerBlockEntityRenderer(GwBlockEntitiesCreate.PARTICLE_ACCELERATOR.get(), ParticleAcceleratorRenderer::new); }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }
}

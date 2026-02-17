package com.jasonpegasus.gigawatt.blockentity;

import com.jasonpegasus.gigawatt.GwBlockEntities;
import com.jasonpegasus.gigawatt.GwBlockEntitiesCreate;
import com.jasonpegasus.gigawatt.GwUtils;
import com.jasonpegasus.gigawatt.block.RepairStationBlock;
import com.jasonpegasus.gigawatt.blockentity.renderer.RepairStationRenderer;
import com.jasonpegasus.gigawatt.custom.GwEnergy;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import com.simibubi.create.content.logistics.depot.DepotBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;

public class RepairStationBlockEntity extends SmartBlockEntity implements Clearable {
    //////////////////////////////////////////// BASIC ////////////////////////////////////////////
    ///
    public RepairStationBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setLazyTickRate(5);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}

    @Override
    public void lazyTick() {
        executeRepair();
        super.lazyTick();
    }

    //////////////////////////////////////////// CAPABILITIES ////////////////////////////////////////////

    public final GwEnergy energy = new GwEnergy(2*GwUtils.M, 80*GwUtils.K, 80*GwUtils.K);

    public final ItemStackHandler inventory = new ItemStackHandler(1){
        @Override
        protected int getStackLimit(int slot, ItemStack stack) { return 1; }

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

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                GwBlockEntitiesCreate.REPAIR_STATION.get(),
                (RepairStationBlockEntity be, Direction side) -> be.inventory
        );
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                GwBlockEntitiesCreate.REPAIR_STATION.get(),
                (RepairStationBlockEntity be, Direction side) -> be.energy
        );
    }

    //////////////////////////////////////////// ITEM STORAGE ////////////////////////////////////////////
    ///
    @Override
    public void clearContent() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++)
        { inv.setItem(i, inventory.getStackInSlot(i)); }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(GwBlockEntitiesCreate.REPAIR_STATION.get(), RepairStationRenderer::new);
    }

    //////////////////////////////////////////// REPAIR BEHAVIOR ////////////////////////////////////////////


    int requiredEnergy = 100*GwUtils.K;
    int energyRepairRatio = 4*GwUtils.K; // 1 REPAIR POINT = 2kFE ENERGY

    public void executeRepair()
    {
        ItemStack item = inventory.getStackInSlot(0);
        if (item.isEmpty() || energy.getEnergyStored() < requiredEnergy) { return; }

        if (item.isRepairable() && item.isDamaged())
        {
            energy.extractEnergy(requiredEnergy, false);
            int dmg = item.getDamageValue() - requiredEnergy/energyRepairRatio;
            item.setDamageValue(Math.clamp(dmg, 0, item.getMaxDamage()));
            level.playSound(null, this.getBlockPos(), SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 0.5f, GwUtils.random(0.5f, 0.5f));
        }
    }

    //////////////////////////////////////////// DATA MANAGEMENT ////////////////////////////////////////////

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("energy", energy.getEnergyStored());
        super.write(tag, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        energy.setEnergy(tag.getInt("energy"));
        super.read(tag, registries, clientPacket);
    }
}

package com.jasonpegasus.gigawatt.blockentity;

import com.jasonpegasus.gigawatt.GwBlockEntitiesCreate;
import com.jasonpegasus.gigawatt.GwFluids;
import com.jasonpegasus.gigawatt.GwUtils;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.pump.PumpBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;
import java.util.List;

public class SteamVentBlockEntity extends SmartBlockEntity {
    public SteamVentBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setLazyTickRate(20);
    }
    PumpBlockEntity pump;

    public IFluidHandler tank = new FluidTank(10000) {};

    @Override
    public void lazyTick() {
        generateSteam();
        pumpSteam();
    }

    //////////////////////////////////////////// STEAM PUMPING ////////////////////////////////////////////

    public void generateSteam(){
        tank.fill(new FluidStack(GwFluids.STEAM, 500), IFluidHandler.FluidAction.EXECUTE);
    }

    static int steamSpeed = 256;
    public void pumpSteam(){
        if (pump == null) { findPump(); return; }
        if (pump.getSpeed() < steamSpeed){
            pump.updateSpeed = false;
            pump.setSpeed(steamSpeed);
            pump.updatePressureChange();
        }
    }

    public void setPump(@Nullable PumpBlockEntity pumpBE) {
        pump = pumpBE;
        if (pump != null)
        { setLazyTickRate(1); }
        else
        { setLazyTickRate(Integer.MAX_VALUE); }
    }

    public void findPump() {
        Direction dir = GwUtils.getBlockFacing(getBlockState());

        BlockState bBack = level.getBlockState(getBlockPos().relative(dir.getOpposite()));

        if (bBack.is(AllBlocks.MECHANICAL_PUMP) && GwUtils.getBlockFacing(bBack) == GwUtils.getBlockFacing(getBlockState()).getOpposite()) {
            BlockEntity pumpBlockEntity = level.getBlockEntity(getBlockPos().relative(dir.getOpposite()));
            if (pumpBlockEntity instanceof PumpBlockEntity){
                setPump((PumpBlockEntity) pumpBlockEntity);
            }
            else setPump(null);
        }
        else setPump(null);
    }

    //////////////////////////////////////////// DATA SAVING ////////////////////////////////////////////

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tag.putInt("steam", tank.getFluidInTank(0).getAmount());
        super.write(tag, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tank.getFluidInTank(0).setAmount(tag.getInt("steam"));
        findPump();
        super.read(tag, registries, clientPacket);
    }


    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                GwBlockEntitiesCreate.STEAM_VENT.get(),
                (SteamVentBlockEntity be, Direction side) -> be.tank
        );
    }


    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {}
}

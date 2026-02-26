package com.jasonpegasus.gigawatt.blockentity;

import com.google.errorprone.annotations.concurrent.LazyInit;
import com.jasonpegasus.gigawatt.*;
import com.jasonpegasus.gigawatt.blockentity.renderer.ItemStackRenderer;
import com.jasonpegasus.gigawatt.blockentity.renderer.ParticleAcceleratorRenderHelper;
import com.jasonpegasus.gigawatt.blockentity.renderer.ParticleAcceleratorRenderer;
import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.registry.CreateBuiltInRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.createmod.catnip.nbt.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PAControllerBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation{
    public PAControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        setLazyTickRate(10);
    }

    //////////////////////////////////////////// CAPABILITIES ////////////////////////////////////////////

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                GwBlockEntitiesCreate.PARTICLE_ACCELERATOR_CONTROLLER.get(),
                (PAControllerBlockEntity be, Direction side) -> be.inventory
        );
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

    @Override
    public void lazyTick() {
        super.lazyTick();
        sortModulePos();
        updateModules();
    }

    //////////////////////////////////////////// BEHAVIOR ////////////////////////////////////////////

    // GLOBAL STATIC STUFF //
    public static int MIN_MODULES = 4;

    // INSTANCED STUFF //
    public ParticleAcceleratorRenderHelper renderHelper;
    public boolean active = false;
    public int speed = 0;


    public List<PAModuleBlockEntity> modules = new ArrayList<>();

    public boolean hasEnoughModules()
    { return modules.size() >= MIN_MODULES; }

    public boolean hasMissingModules(){
        return modules.size() < modulePos.size();
    }

    public boolean canWork(){
        return hasEnoughModules() && !hasMissingModules();
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        // HEADER //
        GwLang.translate("tooltip.particle_accelerator.header")
                .add(Component.literal(" ("+modules.size()+" "))
                .add(GwLang.translatable("tooltip.particle_accelerator.modules"))
                .add(Component.literal("):"))
                .style(ChatFormatting.WHITE).forGoggles(tooltip);

        // MISSING OR LACK OF MODULES ERROR //
        if (!hasEnoughModules()){
            if (hasMissingModules())
            { GwLang.translate("tooltip.particle_accelerator.missingmodules").style(ChatFormatting.DARK_RED).forGoggles(tooltip); }
            else
            { GwLang.translate("tooltip.particle_accelerator.notenoughmodules").style(ChatFormatting.DARK_RED).forGoggles(tooltip); }
        }

        // ACCELERATOR ON/OFF //
        String state = "tooltip.particle_accelerator.state";
        GwLang.translate(state)
                .add(GwLang.translatable(state + (active ? ".active" : ".inactive")))
                .style(ChatFormatting.GRAY).forGoggles(tooltip);

        // ACCELERATOR SPEED //
        GwLang.translate("tooltip.particle_accelerator.speed")
                .add(Component.literal(speed+"%"))
                .style(GwUtils.lerpChatColors(((float)speed)/100,
                        ChatFormatting.DARK_GRAY, ChatFormatting.RED, ChatFormatting.GOLD,
                        ChatFormatting.YELLOW, ChatFormatting.GREEN, ChatFormatting.DARK_GREEN
                )).forGoggles(tooltip);

        return true;
    }

    //////////////////////////////////////////// MODULES ////////////////////////////////////////////

    @Override
    public void initialize() {
        sortModulePos();
        getModulesFromPos();
        renderHelper = new ParticleAcceleratorRenderHelper(this);
        super.initialize();
    }

    public ListTag modulePos = new ListTag();

    private void sortModulePos(){
        Vec3 center = GwUtils.getPolygonCenter(modulePos.stream()
                .map((p -> GwData.deserializeBlockPos((CompoundTag) p))).toList());

        modulePos.sort(Comparator.comparingDouble(posTag -> {
            BlockPos pos = GwData.deserializeBlockPos((CompoundTag) posTag);
            return Math.atan2(pos.getX() - center.x, pos.getZ() - center.z);
        }));
    }

    protected void getModulesFromPos(){
        GwUtils.printSided(level,"READ MODULE POS: " + modulePos.size());
        modules = new ArrayList<>();
        modulePos.stream().map(p -> GwData.deserializeBlockPos((CompoundTag) p))
                .forEach(bp -> modules.add((PAModuleBlockEntity) level.getBlockEntity(bp)));
    }

    public void updateModules() {
        modules = modulePos.stream()
                .map(tag -> level.getBlockEntity(GwData.deserializeBlockPos((CompoundTag) tag)))
                .filter(PAModuleBlockEntity.class::isInstance)
                .map(PAModuleBlockEntity.class::cast).toList();
    }

    public void addModule(BlockPos pos){
        PAModuleBlockEntity mod = (PAModuleBlockEntity) level.getBlockEntity(pos);
        if (mod != null && !modules.contains(mod)) {
            modules.add(mod);
            modulePos.add(GwData.serializeBlockPos(mod));
        }
    }


    //////////////////////////////////////////// SAVE DATA ////////////////////////////////////////////

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tag.put("modules", modulePos);
        GwUtils.printSided(level,"WRITTEN MODULE POS: "+ tag.getList("modules", Tag.TAG_COMPOUND).size());
        super.write(tag, registries, clientPacket);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        modulePos = tag.getList("modules", Tag.TAG_COMPOUND);
        super.read(tag, registries, clientPacket);
    }

    //////////////////////////////////////////// REGISTRATIONS ////////////////////////////////////////////

    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(GwBlockEntitiesCreate.PARTICLE_ACCELERATOR_CONTROLLER.get(), ParticleAcceleratorRenderer::new);
    }

    @Override public void addBehaviours(List<BlockEntityBehaviour> behaviours) { }
}
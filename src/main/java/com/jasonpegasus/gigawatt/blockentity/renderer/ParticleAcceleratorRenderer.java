package com.jasonpegasus.gigawatt.blockentity.renderer;

import com.jasonpegasus.gigawatt.Gigawatt;
import com.jasonpegasus.gigawatt.blockentity.ParticleAcceleratorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.contraptions.actors.contraptionControls.ContraptionControlsBlock;
import com.simibubi.create.content.contraptions.actors.contraptionControls.ContraptionControlsBlockEntity;
import com.simibubi.create.content.contraptions.actors.contraptionControls.ContraptionControlsMovement.ElevatorFloorSelection;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.contraptions.render.ContraptionMatrices;
import com.simibubi.create.content.redstone.DirectedDirectionalBlock;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeRenderer;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.utility.DyeHelper;
import com.simibubi.create.foundation.virtualWorld.VirtualRenderWorld;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ParticleAcceleratorRenderer extends SmartBlockEntityRenderer<ParticleAcceleratorBlockEntity> {
    public ParticleAcceleratorRenderer(Context context) {
        super(context);
    }

    protected static final PartialModel button = PartialModel.of(Gigawatt.asResource("block/particle_accelerator_controller/accelerator_controller_meter"));

    @Override
    protected void renderSafe(ParticleAcceleratorBlockEntity blockEntity, float pt, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        BlockState blockState = blockEntity.getBlockState();
        Direction facing = blockState.getValue(DirectionalBlock.FACING).getOpposite();

        Vec3 buttonMovementAxis = VecHelper.rotate(new Vec3(0, 0, 0), AngleHelper.horizontalAngle(facing), Axis.Y);
        Vec3 buttonMovement = buttonMovementAxis.scale(-0.07f + -1 / 24f);
        Vec3 buttonOffset = buttonMovementAxis.scale(0.07f);

        ms.pushPose();
        ms.translate(buttonMovement.x, buttonMovement.y+0.5, buttonMovement.z);

        super.renderSafe(blockEntity, pt, ms, buffer, light, overlay);

        ms.translate(buttonOffset.x, buttonOffset.y, buttonOffset.z);

        VertexConsumer vc = buffer.getBuffer(RenderType.solid());
        CachedBuffers.partialFacing(button, blockState, facing)
                .light(light)
                .renderInto(ms, vc);

        ms.popPose();
    }
}


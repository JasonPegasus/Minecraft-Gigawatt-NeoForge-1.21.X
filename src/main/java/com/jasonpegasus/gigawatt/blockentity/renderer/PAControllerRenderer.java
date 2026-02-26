package com.jasonpegasus.gigawatt.blockentity.renderer;

import com.jasonpegasus.gigawatt.Gigawatt;
import com.jasonpegasus.gigawatt.blockentity.PAControllerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class PAControllerRenderer extends SmartBlockEntityRenderer<PAControllerBlockEntity> {
    public PAControllerRenderer(Context context) { super(context); }

    protected static final PartialModel button = PartialModel.of(Gigawatt.asResource("block/particle_accelerator_controller/accelerator_controller_meter"));

    @Override
    protected void renderSafe(PAControllerBlockEntity blockEntity, float pt, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        BlockState blockState = blockEntity.getBlockState();
        Direction facing = blockState.getValue(DirectionalBlock.FACING).getOpposite();

        Vec3 buttonMovementAxis = VecHelper.rotate(new Vec3(0, 0, 0), AngleHelper.horizontalAngle(facing), Axis.Y);
        Vec3 buttonMovement = buttonMovementAxis.scale(0);
        Vec3 buttonOffset = buttonMovementAxis.scale(0.07f);

        ms.pushPose();
        ms.translate(buttonMovement.x, buttonMovement.y + (blockEntity.active ? 30 : 0), buttonMovement.z);

        super.renderSafe(blockEntity, pt, ms, buffer, light, overlay);

        ms.translate(buttonOffset.x, buttonOffset.y, buttonOffset.z);

        VertexConsumer vc = buffer.getBuffer(RenderType.solid());
        CachedBuffers.partialFacing(AllPartialModels.CONTRAPTION_CONTROLS_BUTTON, blockState, facing)
                .light(light)
                .renderInto(ms, vc);

        ms.popPose();
    }

}


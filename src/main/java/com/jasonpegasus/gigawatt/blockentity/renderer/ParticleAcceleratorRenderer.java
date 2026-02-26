package com.jasonpegasus.gigawatt.blockentity.renderer;

import com.jasonpegasus.gigawatt.Gigawatt;
import com.jasonpegasus.gigawatt.GwModels;
import com.jasonpegasus.gigawatt.GwUtils;
import com.jasonpegasus.gigawatt.blockentity.PAControllerBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.math.AngleHelper;
import net.createmod.catnip.math.VecHelper;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ParticleAcceleratorRenderer extends SmartBlockEntityRenderer<PAControllerBlockEntity> {
    public ParticleAcceleratorRenderer(Context context) { super(context); }


    @Override
    protected void renderSafe(PAControllerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        ParticleAcceleratorRenderHelper helper = be.renderHelper;
        if (!be.canWork() || helper == null) {
            GwUtils.print("Can work?: "+ be.canWork());
            GwUtils.print("Render Helper is null?: " + (helper == null));
            return;
        }

        int lightIntensity = (int) Mth.lerp(((float) be.speed)/100, 5, 15);
        int glowLight = LightTexture.pack(lightIntensity, lightIntensity);

        BlockState blockState = be.getBlockState();

        SuperByteBuffer headRender = CachedBuffers.partial(GwModels.particle_spin, blockState);
        Vec2 rot = helper.getNextRotation();

        headRender.translate(helper.getNextPosition())
                .rotateDegrees(rot.x, Axis.Y).rotateDegrees(rot.y, Axis.X)
                .light(glowLight)
                .renderInto(ms, buffer.getBuffer(RenderType.translucent()));

    }

    @Override public boolean shouldRenderOffScreen(PAControllerBlockEntity blockEntity) { return true; }
}
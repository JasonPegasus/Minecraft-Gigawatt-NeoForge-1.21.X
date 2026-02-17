package com.jasonpegasus.gigawatt.blockentity.renderer;

import com.jasonpegasus.gigawatt.blockentity.RepairStationBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class RepairStationRenderer implements BlockEntityRenderer<RepairStationBlockEntity> {
    public RepairStationRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(RepairStationBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntity.inventory.getStackInSlot(0);

        poseStack.pushPose();
        poseStack.translate(0.5, 0.4f, 0.5);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, getLightLevel(blockEntity.getLevel(),
                blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, bufferSource, blockEntity.getLevel(), 1);

        poseStack.popPose();
    }

    private int getLightLevel(Level lv, BlockPos pos) {
        int lightBlock = lv.getBrightness(LightLayer.BLOCK, pos);
        int lightSky = lv.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(lightBlock, lightSky);
    }
}

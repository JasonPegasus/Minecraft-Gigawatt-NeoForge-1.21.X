package com.jasonpegasus.gigawatt.item.renderer;

import com.jasonpegasus.gigawatt.Gigawatt;
import com.jasonpegasus.gigawatt.GwUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static java.lang.Math.max;

public class WandOfTransmutationRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel BITS = PartialModel.of(Create.asResource("item/wand_of_symmetry/bits"));
    protected static final PartialModel CORE = PartialModel.of(Gigawatt.asResource("item/wand_of_transmutation_core"));
    protected static final PartialModel CORE_GLOW = PartialModel.of(Gigawatt.asResource("item/wand_of_transmutation_glow"));

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        float worldTime = AnimationTickHolder.getRenderTime() / 20;
        int maxLight = LightTexture.FULL_BRIGHT;

        int lightIntensity = (int) (15 * (Mth.sin(worldTime*2)*0.3+0.7));
        int glowLight = LightTexture.pack(lightIntensity, lightIntensity);

        renderer.render(model.getOriginalModel(), light);
        renderer.renderSolidGlowing(CORE.get(), glowLight);
        ms.translate(0, 0.01f, 0);
        renderer.renderGlowing(CORE_GLOW.get(), glowLight);

        float floating = Mth.sin(worldTime) * .05f;
        float angle = worldTime * -10 % 360;

        ms.translate(0, floating, 0);
        ms.mulPose(Axis.YP.rotationDegrees(angle));
        ms.scale(1.3f, 1.3f, 1.3f);
        renderer.renderGlowing(BITS.get(), maxLight);
    }
}

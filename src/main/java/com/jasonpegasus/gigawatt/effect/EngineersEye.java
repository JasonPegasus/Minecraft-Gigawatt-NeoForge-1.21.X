package com.jasonpegasus.gigawatt.effect;

import com.jasonpegasus.gigawatt.GwEffects;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class EngineersEye extends MobEffect {
    static{ GogglesItem.addIsWearingPredicate(player -> player.hasEffect(GwEffects.ENGINEERS_EYE)); }

    public EngineersEye(MobEffectCategory category, int color) {
        super(category, color);
    }
}

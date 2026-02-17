package com.jasonpegasus.gigawatt;

import com.jasonpegasus.gigawatt.effect.EngineersEye;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GwEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Gigawatt.MOD_ID);

    public static final Holder<MobEffect> ENGINEERS_EYE = EFFECTS.register("engineers_eye", () -> new EngineersEye(MobEffectCategory.NEUTRAL, 0xFCF085));

    public static void register(IEventBus eventBus)
    { EFFECTS.register(eventBus); }
}

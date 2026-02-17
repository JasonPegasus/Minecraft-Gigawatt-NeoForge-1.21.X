package com.jasonpegasus.gigawatt.block;

import com.jasonpegasus.gigawatt.GwUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class UraniumRodsBlock extends RotatedPillarBlock {
    public UraniumRodsBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof LivingEntity && (((int) level.getDayTime()) % 10 == 0)) {
            entity.invulnerableTime = 0;
            entity.hurt(level.damageSources().inFire(), 2.0F);
            if (GwUtils.randomInt(1, 50) == 1 && !level.isClientSide){
                ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 10));
            }
        }
        super.stepOn(level, pos, state, entity);
    }
}
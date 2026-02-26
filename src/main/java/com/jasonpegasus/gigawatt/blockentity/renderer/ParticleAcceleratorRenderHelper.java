package com.jasonpegasus.gigawatt.blockentity.renderer;

import com.jasonpegasus.gigawatt.GwUtils;
import com.jasonpegasus.gigawatt.blockentity.PAControllerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.net.CacheRequest;
import java.util.Arrays;

public class ParticleAcceleratorRenderHelper {
    PAControllerBlockEntity controller;
    int nextModule = 0;
    Vec3 currPos;
    Vec3[] modules;
    Level level;

    public ParticleAcceleratorRenderHelper(PAControllerBlockEntity controllerBE) {
        controller = controllerBE;
        checkCorrectData();
    }

    public Vec3 getNextPosition(){
        checkCorrectData();
        if (currPos.distanceTo(nextPos()) < 0.1f)
        { nextModule = GwUtils.addLooped(nextModule, modules.length-1); }
        currPos = moveTo(currPos, nextPos(), 1); // speed in blocks per second

        // ALWAYS RETURNS currPos //
        return currPos.subtract(toVec3(controller.getBlockPos())).add(0.5, 0.2, 0.5);
    }

    public Vec2 getNextRotation(){
        checkCorrectData();
        Vec3 dir = nextPos().subtract(currPos);
        if (dir == Vec3.ZERO) { return Vec2.ZERO; }

        float yaw = (float) Math.toDegrees(Math.atan2(dir.x, dir.z));
        float pitch = (float) Math.toDegrees(-Math.atan2(dir.y, dir.horizontalDistance()));
        return new Vec2(yaw, pitch);
    }

    private void checkCorrectData(){
        if (modules == null || level == null || Arrays.asList(modules).contains(null)){
            modules = controller.modules.stream().map(bp -> toVec3(bp.getBlockPos())).toArray(Vec3[]::new);
        }
        if (currPos == null)
        { currPos = (modules[0] != null) ? modules[0] : toVec3(controller.getBlockPos()); }
    }

    private Vec3 moveTo(Vec3 from, Vec3 to, double speed){
        Vec3 dir = to.subtract(from).normalize();
        double dist = from.distanceTo(to);
        return from.add(dir.scale(Math.min(speed/20, dist)));
    }

    private Vec3 nextPos()
    { return modules[nextModule]; }

    private Vec3 toVec3(BlockPos bp)
    { return new Vec3(bp.getX(), bp.getY(), bp.getZ()); }
}

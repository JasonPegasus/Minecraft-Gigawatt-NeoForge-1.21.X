package com.jasonpegasus.gigawatt;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public class GwData {
    public static CompoundTag serializeBlockPos(BlockEntity be){;
        CompoundTag nbt = new CompoundTag();
        nbt.put("blockPos", NbtUtils.writeBlockPos(be.getBlockPos()));
        return nbt;
    }

    public static BlockPos deserializeBlockPos(CompoundTag nbt){
        return readBlockPos(nbt, "blockPos");
    }


    public static BlockPos readBlockPos(CompoundTag nbt, String key) {
        Optional<BlockPos> pos = NbtUtils.readBlockPos(nbt, key);
        if (pos.isPresent())
            return pos.get();
        CompoundTag oldTag = nbt.getCompound(key);
        return new BlockPos(oldTag.getInt("X"), oldTag.getInt("Y"), oldTag.getInt("Z"));
    }
}

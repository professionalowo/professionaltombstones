package com.professionalowo.util

import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes

fun VoxelShape.or(other: VoxelShape): VoxelShape =
    VoxelShapes.union(this, other)



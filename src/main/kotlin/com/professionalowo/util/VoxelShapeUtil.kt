package com.professionalowo.util

import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes

fun Iterable<VoxelShape>.reduceOr(): VoxelShape = reduce { v1: VoxelShape, v2: VoxelShape -> v1.reduceOr(v2) }

fun VoxelShape.reduceOr(other: VoxelShape): VoxelShape =
    VoxelShapes.combineAndSimplify(this, other, BooleanBiFunction.OR)
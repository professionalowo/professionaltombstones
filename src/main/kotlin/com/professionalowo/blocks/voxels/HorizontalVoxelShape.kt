package com.professionalowo.blocks.voxels

import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape

data class HorizontalVoxelShape(
    val north: VoxelShape,
    val east: VoxelShape,
    val south: VoxelShape,
    val west: VoxelShape
) {
    /**
     * @return the VoxelShape associated with the Direction or north
     */
    operator fun get(direction: Direction?): VoxelShape =
        when (direction) {
            Direction.NORTH -> north
            Direction.EAST -> east
            Direction.SOUTH -> south
            Direction.WEST -> west
            else -> north
        }
}

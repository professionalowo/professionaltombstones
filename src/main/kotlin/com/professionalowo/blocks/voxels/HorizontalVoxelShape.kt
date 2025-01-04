package com.professionalowo.blocks.voxels

import com.professionalowo.util.rotateShape
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import com.professionalowo.util.or

/**
 * Contains a VoxelShape for each horizontal Direction
 */
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

/**
 * @return a HorizontalVoxelShape made from the provided shape rotated to each horizontal Direction
 */
fun createHorizontalVoxelShape(north: VoxelShape): HorizontalVoxelShape =
    HorizontalVoxelShape(
        north.rotateShape(Direction.NORTH, Direction.NORTH),
        north.rotateShape(Direction.NORTH, Direction.EAST),
        north.rotateShape(Direction.NORTH, Direction.SOUTH),
        north.rotateShape(Direction.NORTH, Direction.WEST)
    )

/**
 * @see createHorizontalVoxelShape
 */
inline fun createHorizontalVoxelShape(northSupplier: () -> VoxelShape) = createHorizontalVoxelShape(northSupplier())

/**
 * @see createHorizontalVoxelShape
 *
 * Creates a union of northShapes, then creates a HorizontalVoxelShape
 */
fun createHorizontalVoxelShape(vararg northShapes: VoxelShape) =
    createHorizontalVoxelShape { northShapes.reduce { v1, v2 -> v1.or(v2) } }

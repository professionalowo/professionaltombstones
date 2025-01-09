package com.professionalowo.util

import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes

fun VoxelShape.or(other: VoxelShape): VoxelShape =
    VoxelShapes.union(this, other)

/**
 * Rotates a Shape
 * @param from the original direction this is facing
 * @param to the direction this should be rotated to
 * @return the rotated VoxelShape
 */
fun VoxelShape.rotateShape(from: Direction, to: Direction): VoxelShape =
    this.applyTransform(rotationTimes(from, to))


private fun rotationTimes(from: Direction, to: Direction): Int = (to.horizontal - from.horizontal + 4) % 4

private tailrec fun VoxelShape.applyTransform(times: Int): VoxelShape =
    when (times) {
        0 -> this
        //transform the pair again, swap the pair
        else -> transformSingleShape(this).applyTransform(times - 1)
    }

private fun transformSingleShape(shape: VoxelShape): VoxelShape =
    buildList {
        shape.forEachBox { minX, minY, minZ, maxX, maxY, maxZ ->
            add(VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))
        }
    }.fold(VoxelShapes.empty()) { acc, cuboid -> acc.or(cuboid) }

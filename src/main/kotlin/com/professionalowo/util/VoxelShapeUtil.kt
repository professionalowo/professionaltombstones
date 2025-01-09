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
    this.rotateNTimes(rotationTimes(from, to))

/**
 * @param from the start direction
 * @param to the target direction
 * @return how often to rotate on the Y-Axis to reach to
 */
private fun rotationTimes(from: Direction, to: Direction): Int = (to.horizontal - from.horizontal + 4) % 4

/**
 * Rotate this on the Y-Axis by n * 90%
 * @param n the number of times to rotate this
 * @return the result of rotating this n times
 */
private tailrec fun VoxelShape.rotateNTimes(n: Int): VoxelShape =
    when (n) {
        0 -> this
        else -> transformSingleShape(this).rotateNTimes(n.dec())
    }

/**
 * Rotate shape by 90% on the Y-Axis
 * @param shape the shape to rotate
 * @return the shape after being rotated
 */
private fun transformSingleShape(shape: VoxelShape): VoxelShape =
    buildList {
        shape.forEachBox { minX, minY, minZ, maxX, maxY, maxZ ->
            add(VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))

        }
    }.fold(VoxelShapes.empty()) { acc, cuboid -> acc.or(cuboid) }

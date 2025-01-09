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
    if (from == to) this else {
        val times = (to.horizontal - from.horizontal + 4) % 4
        this.applyTransform(times)
    }


private fun VoxelShape.applyTransform(times: Int): VoxelShape =
    applyTransform(times, Pair(this, VoxelShapes.empty())).first;

private tailrec fun applyTransform(times: Int, buffer: Pair<VoxelShape, VoxelShape>): Pair<VoxelShape, VoxelShape> =
    when (times) {
        0 -> buffer
        //transform the pair again, swap the pair
        else -> applyTransform(times - 1, transformSinglePair(buffer).run { Pair(second, VoxelShapes.empty()) })
    }


private fun transformSinglePair(pair: Pair<VoxelShape, VoxelShape>): Pair<VoxelShape, VoxelShape> =
    pair.run {
        Pair(
            first,
            first.boxes()
                .map { it.cuboidRotated() }
                .fold(second) { acc, cuboid -> acc.or(cuboid) })
    }

private fun VoxelShape.boxes(): List<Box> {
    val boxes = mutableListOf<Box>()
    forEachBox { minX, minY, minZ, maxX, maxY, maxZ -> boxes.add(Box(minX, minY, minZ, maxX, maxY, maxZ)) }
    return boxes
}


private data class Box(
    val minX: Double,
    val minY: Double,
    val minZ: Double,
    val maxX: Double,
    val maxY: Double,
    val maxZ: Double
) {
    fun cuboidRotated(): VoxelShape =
        VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX) ?: VoxelShapes.empty()
}

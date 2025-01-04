package com.professionalowo.util

import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes

fun VoxelShape.or(other: VoxelShape): VoxelShape =
    VoxelShapes.union(this, other)


fun VoxelShape.rotateShape(from: Direction, to: Direction): VoxelShape {
    if (from == to) return this

    val buffer = arrayOf(this, VoxelShapes.empty())
    val times = (to.horizontal - from.horizontal + 4) % 4
    for (i in 0 until times) {
        buffer[0].forEachBox { minX, minY, minZ, maxX, maxY, maxZ ->
            buffer[1] = buffer[1].or(VoxelShapes.cuboid(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))
        }
        buffer[0] = buffer[1]
        buffer[1] = VoxelShapes.empty()
    }

    return buffer[0]
}

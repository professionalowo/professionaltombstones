package com.professionalowo.util

import net.minecraft.block.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Returns the next solid block under this block, if there is no block, there will be a Block of dirt
 *
 * @param world the World
 * @return the BlockPos of the next solid block under this
 */
tailrec fun BlockPos.nextSolidBlockDown(world: World): BlockPos {
    val newPos = withY(y - 1)
    if (world.bottomY >= newPos.y) {
        world.setBlockState(newPos.withY(world.bottomY), Blocks.DIRT.defaultState)
        return newPos.withY(world.bottomY + 1)
    }

    return if (world.isSolidBlock(newPos)) {
        this
    } else {
        newPos.nextSolidBlockDown(world)
    }
}


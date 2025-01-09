package com.professionalowo.util

import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Checks if the block specified by pos is a solid block
 *
 * @param pos the BlockPos to check
 * @return if the Block at pos in this is solid
 */
fun World.isSolidBlock(pos: BlockPos) = getBlockState(pos).isSolidBlock(getChunkAsView(pos.x, pos.z), pos)
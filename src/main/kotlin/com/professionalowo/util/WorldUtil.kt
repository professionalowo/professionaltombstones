package com.professionalowo.util

import net.minecraft.particle.ParticleEffect
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

/**
 * Checks if the block specified by [pos] is a solid block
 *
 * @param pos the BlockPos to check
 * @return if the Block at pos in this is solid
 */
fun World.isSolidBlock(pos: BlockPos) = getBlockState(pos).isSolidBlock(getChunkAsView(pos.x, pos.z), pos)

fun World.addParticle(effect: ParticleEffect, pos: Vec3d, velocity: Vec3d) =
    velocity.run { addParticle(effect, pos.x, pos.y, pos.z, x, y, z) }

package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import com.professionalowo.util.*
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import kotlin.math.abs

class AltarCoreBlock(settings: Settings) : AbstractAltarBlock(settings) {
    companion object {
        val voxelShape =
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0).or(createCuboidShape(2.0, 7.0, 2.0, 14.0, 9.0, 14.0))


        val PEDESTAL_OFFSETS: List<BlockPos> = BlockPos.stream(-2, 0, -2, 2, 0, 2)
            .filter { pos -> abs(pos.x.toDouble()) == 2.0 || abs(pos.z.toDouble()) == 2.0 }
            .filter { pos -> abs(pos.x.toDouble()) != abs(pos.z.toDouble()) }
            .map { it.toImmutable() }
            .toList()

        fun canAccessPedestals(world: World, corePos: BlockPos, offset: BlockPos): Boolean {
            val blockState = world.getBlockState(corePos.add(offset))
            val isBlocked: Boolean by lazy { world.isSolidBlock(corePos.add(offset.x / 2, offset.y, offset.z / 2)) }
            return blockState.block is AltarPedestalBlock && !isBlocked
        }
    }

    private fun getPossiblePedestalPositions(pos: BlockPos) = PEDESTAL_OFFSETS.mapNotNull { pos.add(it).toImmutable() }
    private fun getPedestalBlockEntities(world: World, pos: BlockPos) =
        getPossiblePedestalPositions(pos).mapNotNull { world.getBlockEntity(it) as? AltarBlockEntity }

    private fun hasFullPedestals(world: World, pos: BlockPos) = PEDESTAL_OFFSETS.all { offset ->
        canAccessPedestals(world, pos, offset)
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val arePedestalsFull: Boolean by lazy { hasFullPedestals(world, pos) }
        if (random.nextInt(5) == 0 && arePedestalsFull) {
            for (offset in PEDESTAL_OFFSETS) {
                if (random.nextInt(3) != 0) continue
                val (pX, pY, pZ) = pos + offset
                val (vX, vY, vZ) = -offset
                world.addParticle(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    pX.toDouble() + 0.5,
                    pY.toDouble() + 1,
                    pZ.toDouble() + 0.5,
                    vX.toDouble() * 0.05,
                    vY.toDouble() * 0.05,
                    vZ.toDouble() * 0.05,
                )
            }
        }
        if (random.nextInt(5) == 0 && arePedestalsFull) {
            world.playSoundAtBlockCenter(
                pos,
                SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                SoundCategory.BLOCKS,
                1f,
                1f,
                true
            )
        }
    }

    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { AltarCoreBlock(it) }

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return voxelShape
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hit: BlockHitResult?
    ): ActionResult {
        return super.onUse(state, world, pos, player, hit)
    }
}
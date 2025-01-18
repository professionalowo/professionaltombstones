package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.util.*
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
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
            .filter { abs(it.x) == 2 || abs(it.z) == 2 }
            .filter { abs(it.x) != abs(it.z) }
            .map { it.toImmutable() }
            .toList()

        private fun canAccessPedestal(world: World, corePos: BlockPos, offset: BlockPos): Boolean {
            val blockState = world.getBlockState(corePos.add(offset))
            val isBlocked: Boolean by lazy { world.isSolidBlock(corePos.add(offset.x / 2, offset.y, offset.z / 2)) }
            return blockState.block is AltarPedestalBlock && !isBlocked
        }

        private fun getPossiblePedestalPositions(pos: BlockPos) = PEDESTAL_OFFSETS.mapNotNull { pos.add(it).toImmutable() }

        fun getPedestalBlockEntities(world: World, pos: BlockPos) =
            getPossiblePedestalPositions(pos).mapNotNull { world.getBlockEntity(it) as? AbstractAltarBlockEntity }

        fun hasFullPedestals(world: World, pos: BlockPos) =
            PEDESTAL_OFFSETS.all { canAccessPedestal(world, pos, it) }
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = AltarCoreBlockEntity(pos, state)


    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val arePedestalsFull: Boolean by lazy { hasFullPedestals(world, pos) }
        if (random.nextInt(5) == 0 && arePedestalsFull) {
            for (offset in PEDESTAL_OFFSETS) {
                if (random.nextInt(3) != 0) continue
                world.addParticle(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    Vec3d.ofCenter(pos + offset),
                    -Vec3d.of(offset).multiply(0.05)
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
    ): VoxelShape = voxelShape

    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? = if (world.isClient) {
        validateTicker(type, ModBlockEntities.ALTAR_CORE_BLOCK_ENTITY) { w, p, s, e ->
            e.tick(w, p, s)
        }
    } else null

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
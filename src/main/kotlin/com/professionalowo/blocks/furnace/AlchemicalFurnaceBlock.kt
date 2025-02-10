package com.professionalowo.blocks.furnace

import com.mojang.serialization.MapCodec
import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.world.World


class AlchemicalFurnaceBlock(settings: Settings) : AbstractFurnaceBlock(settings) {
    companion object {
        val CODEC: MapCodec<AlchemicalFurnaceBlock> = createCodec { AlchemicalFurnaceBlock(it) }
    }

    override fun getCodec(): MapCodec<out AbstractFurnaceBlock> = CODEC

    override fun createBlockEntity(pos: BlockPos, state: BlockState?): BlockEntity =
        AlchemicalFurnaceBlockEntity(pos, state)

    override fun openScreen(world: World, pos: BlockPos, player: PlayerEntity) {
        val blockEntity = world.getBlockEntity(pos) as? AlchemicalFurnaceBlockEntity ?: return
        player.openHandledScreen(blockEntity)
    }

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? = validateTicker(world, type, ModBlockEntities.ALCHEMICAL_FURNACE_BLOCK_ENTITY)

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state.get(LIT)) {
            val d = pos.x.toDouble() + 0.5
            val e = pos.y.toDouble()
            val f = pos.z.toDouble() + 0.5
            if (random.nextDouble() < 0.1) {
                world.playSound(
                    d, e, f,
                    SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE,
                    SoundCategory.BLOCKS,
                    1.0f,
                    1.0f,
                    false
                )
            }
            val direction = state.get(FACING)
            val axis = direction.axis
            val h = random.nextDouble() * 0.6 - 0.3
            val i = if (axis === Direction.Axis.X) direction.offsetX.toDouble() * 0.52 else h
            val j = random.nextDouble() * 6.0 / 16.0
            val k = if (axis === Direction.Axis.Z) direction.offsetZ.toDouble() * 0.52 else h
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0)
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0)
        }
    }
}
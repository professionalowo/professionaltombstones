package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.util.or
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class AltarPedestalBlock(settings: Settings) : AbstractAltarBlock(settings) {
    companion object {
        private val voxelShape =
            createCuboidShape(3.0, 0.0, 3.0, 13.0, 9.0, 13.0).or(createCuboidShape(2.0, 9.0, 2.0, 14.0, 11.0, 14.0))
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = AltarPedestalBlockEntity(pos, state)

    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { AltarPedestalBlock(it) }

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
        validateTicker(type, ModBlockEntities.ALTAR_PEDESTAL_BLOCK_ENTITY) { _, _, _, e ->
            e.ticks++
        }
    } else null
}
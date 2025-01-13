package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import com.professionalowo.util.or
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

class AltarPedestalBlock(settings: Settings) : AbstractAltarBlock(settings) {
    companion object {
        private val voxelShape =
            createCuboidShape(3.0, 0.0, 3.0, 13.0, 9.0, 13.0).or(createCuboidShape(2.0, 9.0, 2.0, 14.0, 11.0, 14.0))
    }

    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { AltarPedestalBlock(it) }

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = voxelShape
}
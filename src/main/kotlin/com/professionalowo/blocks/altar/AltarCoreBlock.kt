package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import com.professionalowo.util.or
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView

class AltarCoreBlock(settings: Settings) : AbstractAltarBlock(settings) {
    companion object {
        val voxelShape =
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0).or(createCuboidShape(2.0, 7.0, 2.0, 14.0, 9.0, 14.0))
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
}
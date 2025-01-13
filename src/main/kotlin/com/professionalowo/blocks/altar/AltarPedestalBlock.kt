package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class AltarPedestalBlock(settings: Settings) : AbstractAltarBlock(settings) {
    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { AltarPedestalBlock(it) }

    override fun createBlockEntity(pos: BlockPos, state: BlockState?): BlockEntity =
        AltarPedestalBlockEntity(pos, state)
}
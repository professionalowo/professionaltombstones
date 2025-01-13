package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import net.minecraft.block.AbstractBlock
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos

class AltarCoreBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { AltarCoreBlock(it) }

    override fun createBlockEntity(pos: BlockPos, state: BlockState?): BlockEntity = AltarCoreBlockEntity(pos, state)
}
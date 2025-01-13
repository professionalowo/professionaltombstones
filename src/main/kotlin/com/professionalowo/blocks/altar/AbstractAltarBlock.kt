package com.professionalowo.blocks.altar

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity

abstract class AbstractAltarBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL
}
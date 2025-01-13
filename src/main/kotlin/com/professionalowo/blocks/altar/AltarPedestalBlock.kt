package com.professionalowo.blocks.altar

import com.mojang.serialization.MapCodec
import net.minecraft.block.BlockWithEntity

class AltarPedestalBlock(settings: Settings) : AbstractAltarBlock(settings) {
    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { AltarPedestalBlock(it) }
}
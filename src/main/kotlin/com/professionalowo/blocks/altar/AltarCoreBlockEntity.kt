package com.professionalowo.blocks.altar

import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

class AltarCoreBlockEntity(pos: BlockPos, state: BlockState) :
    AbstractAltarBlockEntity(ModBlockEntities.ALTAR_CORE_BLOCK_ENTITY, pos, state)
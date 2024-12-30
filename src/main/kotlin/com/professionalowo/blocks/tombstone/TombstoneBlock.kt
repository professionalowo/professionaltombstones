package com.professionalowo.blocks.tombstone

import com.mojang.serialization.MapCodec
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.Waterloggable
import net.minecraft.block.entity.BlockEntity
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.state.property.Properties

class TombstoneBlock(settings: Settings) : BlockWithEntity(settings), Waterloggable {
    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { TombstoneBlock(it) }

    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = TombstoneBlockEntity(pos, state)

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(HorizontalFacingBlock.FACING, Properties.WATERLOGGED)
    }
}
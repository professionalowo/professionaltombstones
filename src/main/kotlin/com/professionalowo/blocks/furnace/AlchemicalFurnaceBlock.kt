package com.professionalowo.blocks.furnace

import com.mojang.serialization.MapCodec
import net.minecraft.block.AbstractFurnaceBlock
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
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
}
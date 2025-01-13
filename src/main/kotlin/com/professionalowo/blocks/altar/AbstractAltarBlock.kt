package com.professionalowo.blocks.altar

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.ItemActionResult
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class AbstractAltarBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun onUseWithItem(
        stack: ItemStack,
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand?,
        hit: BlockHitResult?
    ): ItemActionResult {
        val entity = world.getBlockEntity(pos) as? AbstractAltarBlockEntity ?: return ItemActionResult.FAIL
        val existing = entity.item.copy()
        entity.clear()
        val playerItemStack = player.getStackInHand(hand)
        entity.item = playerItemStack.copyWithCount(1)

        if (!player.isCreative)
            playerItemStack.decrement(1)

        player.inventory.insertStack(existing)

        return ItemActionResult.SUCCESS
    }

    override fun onStateReplaced(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        newState: BlockState?,
        moved: Boolean
    ) {
        ItemScatterer.onStateReplaced(state, newState, world, pos)
        super.onStateReplaced(state, world, pos, newState, moved)
    }
}
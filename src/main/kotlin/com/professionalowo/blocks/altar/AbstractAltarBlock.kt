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
        hand: Hand,
        hit: BlockHitResult
    ): ItemActionResult =
        ((world.getBlockEntity(pos) as? AbstractAltarBlockEntity)?.let { swapItems(it, player, hand) })
            ?: ItemActionResult.FAIL


    protected fun swapItems(entity: AbstractAltarBlockEntity, player: PlayerEntity, hand: Hand): ItemActionResult =
        entity.runCatching {
            val existing = item.copy()
            clear()
            val playerItemStack = player.getStackInHand(hand)
            item = playerItemStack.copyWithCount(1)

            if (!player.isCreative)
                playerItemStack.decrement(1)

            player.inventory.insertStack(existing)

            return ItemActionResult.SUCCESS
        }.getOrElse { ItemActionResult.FAIL }

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
package com.professionalowo.blocks.altar

import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.ItemActionResult
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

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
    ): ItemActionResult {
        val entity = world.getBlockEntity(pos) as? AbstractAltarBlockEntity ?: return ItemActionResult.FAIL

        return swapItems(entity, player, hand).also { entity.markDirty() }
    }


    private fun swapItems(
        entity: AbstractAltarBlockEntity,
        player: PlayerEntity,
        hand: Hand
    ): ItemActionResult =
        entity.runCatching {
            val existing = getStack(0).copy()
            val world = player.world
            clear()
            val playerItemStack = player.getStackInHand(hand)

            if (existing.isEmpty && playerItemStack.isEmpty) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            world.run {
                if (isClient) playSoundAtBlockCenter(
                    pos,
                    SoundEvents.ENTITY_ITEM_FRAME_PLACE,
                    SoundCategory.BLOCKS,
                    2f,
                    1f,
                    true
                )
            }
            if (!world.isClient)
                setStack(0, playerItemStack.copyWithCount(1))

            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos)

            playerItemStack.decrementUnlessCreative(1, player)

            player.inventory.offerOrDrop(existing)

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
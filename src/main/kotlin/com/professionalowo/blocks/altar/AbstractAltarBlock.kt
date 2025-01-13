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

abstract class AbstractAltarBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState?): BlockEntity? = AltarBlockEntity(pos, state)

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
        ((world.getBlockEntity(pos) as? AltarBlockEntity)?.let { swapItems(it, player, hand) })
            ?: ItemActionResult.FAIL


    private fun swapItems(entity: AltarBlockEntity, player: PlayerEntity, hand: Hand): ItemActionResult =
        entity.runCatching {
            val existing = item.copy()
            clear()
            val playerItemStack = player.getStackInHand(hand)

            if (existing.isEmpty && playerItemStack.isEmpty) return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
            player.world.run {
                if (isClient) playSoundAtBlockCenter(
                    pos,
                    SoundEvents.ENTITY_ITEM_FRAME_PLACE,
                    SoundCategory.BLOCKS,
                    2f,
                    1f,
                    true
                )
            }


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

    override fun <T : BlockEntity?> getTicker(
        world: World,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? = if (world.isClient) {
        validateTicker(type, ModBlockEntities.ALTAR_BLOCK_ENTITY) { w, p, s, e ->
            e.ticks++
        }
    } else null
}
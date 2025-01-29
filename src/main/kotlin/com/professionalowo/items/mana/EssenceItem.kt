package com.professionalowo.items.mana

import com.professionalowo.player_data.PlayerDataAccessor
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

abstract class EssenceItem(settings: Settings) : Item(settings) {
    abstract fun getEssenceCost(player: PlayerEntity): Int

    protected open fun canPlayerUse(player: PlayerEntity): Boolean =
        getEssenceCost(player) < PlayerDataAccessor(player).essence

    protected open fun consumePlayerEssence(player: PlayerEntity) {
        PlayerDataAccessor(player).decrementEssence(getEssenceCost(player))
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> =
        if (canPlayerUse(user)) {
            doUse(world, user, hand).also {
                if (it.result.isAccepted) {
                    consumePlayerEssence(user)
                }
            }
        } else TypedActionResult.fail(user.getStackInHand(hand))

    open fun doUse(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> =
        TypedActionResult.pass(user.getStackInHand(hand))
}
package com.professionalowo.items.mana

import com.professionalowo.player_data.PlayerDataAccessor
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

abstract class ManaItem(settings: Settings) : Item(settings) {
    abstract fun getManaCost(player: PlayerEntity): Int

    protected open fun canPlayerUse(player: PlayerEntity): Boolean = getManaCost(player) < PlayerDataAccessor(player).mana

    protected open fun consumePlayerMana(player: PlayerEntity) {
        PlayerDataAccessor(player).decrementMana(getManaCost(player))
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> =
        if (canPlayerUse(user)) {
            val result = doUse(world, user, hand)
            if(result.result.isAccepted) {
                consumePlayerMana(user)
            }
            result
        } else TypedActionResult.fail(user.getStackInHand(hand))

    open fun doUse(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> =
        TypedActionResult.success(user.getStackInHand(hand))
}
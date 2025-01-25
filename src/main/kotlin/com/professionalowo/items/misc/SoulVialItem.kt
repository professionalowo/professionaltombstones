package com.professionalowo.items.misc

import com.professionalowo.items.ModItems
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.ZombieVillagerEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

class SoulVialItem(settings: Settings) : Item(settings) {
    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        return if (entity is ZombieVillagerEntity) {
            if (entity.health > 5) return ActionResult.FAIL
            stack.decrementUnlessCreative(1, user)
            entity.kill()
            user.inventory.offerOrDrop(ItemStack(ModItems.FILLED_SOUL_VIAL))
            ActionResult.CONSUME
        } else super.useOnEntity(stack, user, entity, hand)
    }
}
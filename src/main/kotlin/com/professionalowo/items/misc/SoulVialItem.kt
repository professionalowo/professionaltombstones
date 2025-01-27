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
    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult =
        if (entity is ZombieVillagerEntity) useOnZombieVillagerEntity(stack, user, entity)
        else super.useOnEntity(stack, user, entity, hand)


    private fun useOnZombieVillagerEntity(
        stack: ItemStack,
        user: PlayerEntity,
        entity: ZombieVillagerEntity
    ): ActionResult =
        if (entity.health > 5) ActionResult.FAIL else {
            stack.decrementUnlessCreative(1, user)
            entity.kill()
            user.inventory.offerOrDrop(ItemStack(ModItems.FILLED_SOUL_VIAL))
            ActionResult.CONSUME
        }
}
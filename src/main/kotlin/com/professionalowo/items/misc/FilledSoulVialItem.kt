package com.professionalowo.items.misc

import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.world.World
import kotlin.random.Random
import kotlin.random.nextInt

class FilledSoulVialItem(settings: Settings) : Item(settings) {
    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        tooltip.add(Text.translatable("filled_soul_vial_tooltip").styled {
            it.withColor(Formatting.GRAY)
        })
        super.appendTooltip(stack, context, tooltip, type)
    }

    override fun hasGlint(stack: ItemStack?): Boolean = true

    override fun inventoryTick(stack: ItemStack, world: World, entity: Entity, slot: Int, selected: Boolean) {
        if (!selected) return
        val sounds = arrayOf(
            SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT,
            SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT,
            SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED
        )
        val random = Random(System.nanoTime()).nextInt(0 until 200)
        if (random >= 198) {
            world.playSound(
                entity, entity.blockPos, sounds.random(),
                SoundCategory.BLOCKS, 1f, 1f
            )
        }
    }
}
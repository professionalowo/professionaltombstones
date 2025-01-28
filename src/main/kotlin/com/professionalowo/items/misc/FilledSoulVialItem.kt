package com.professionalowo.items.misc

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.Formatting

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
}
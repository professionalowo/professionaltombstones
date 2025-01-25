package com.professionalowo.items.swords

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.*
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.Formatting
class WitherSwordItem(material: ToolMaterial, settings: Settings) : SwordItem(
    material, settings,
) {
    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        target.addStatusEffect(
            StatusEffectInstance(StatusEffects.WITHER, 60, 3, false, true, true)
        )
        return super.postHit(stack, target, attacker)
    }

    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        tooltip.add(Text.translatable("wither_sword_tooltip").styled { style ->
            style.withColor(Formatting.DARK_PURPLE)
        })
        super.appendTooltip(stack, context, tooltip, type)
    }
}
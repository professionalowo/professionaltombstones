package com.professionalowo.items.swords

import net.fabricmc.fabric.api.item.v1.EnchantingContext
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterials
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Rarity

class WitherSwordItem : SwordItem(
    ToolMaterials.NETHERITE, Settings().fireproof().rarity(Rarity.EPIC).attributeModifiers(
        createAttributeModifiers(ToolMaterials.NETHERITE, 5, -2.4f)
    )
) {
    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        val effect = StatusEffectInstance(StatusEffects.WITHER, 60, 3, false, true, true)
        target.addStatusEffect(effect)
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

    override fun canBeEnchantedWith(
        stack: ItemStack?,
        enchantment: RegistryEntry<Enchantment>?,
        context: EnchantingContext?
    ): Boolean {
        return super.canBeEnchantedWith(stack, enchantment, context)
    }
}
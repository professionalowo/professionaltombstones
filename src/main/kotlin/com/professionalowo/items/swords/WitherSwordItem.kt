package com.professionalowo.items.swords

import com.professionalowo.util.modIdentifier
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ItemStack
import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterials
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.Rarity

class WitherSwordItem : SwordItem(
    ToolMaterials.NETHERITE, Settings().fireproof().rarity(Rarity.EPIC).attributeModifiers(
        createAttributeModifiers(ToolMaterials.NETHERITE, 5, -2.4f)
    )
) {
    override fun postDamageEntity(stack: ItemStack, target: LivingEntity, attacker: LivingEntity) {
        val effect = StatusEffectInstance(StatusEffects.WITHER, 60, 3, false, true, true)
        target.addStatusEffect(effect)
        super.postDamageEntity(stack, target, attacker)
    }

    override fun hasGlint(stack: ItemStack?): Boolean = true

    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        tooltip.add(Text.translatable("wither_sword_tooltip"))
        super.appendTooltip(stack, context, tooltip, type)
    }
}
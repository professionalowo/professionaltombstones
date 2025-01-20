package com.professionalowo.items.swords

import net.minecraft.item.SwordItem
import net.minecraft.item.ToolMaterials
import net.minecraft.util.Rarity

class WitherSwordItem : SwordItem(
    ToolMaterials.NETHERITE, Settings().fireproof().rarity(Rarity.EPIC).attributeModifiers(
        createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.4f)
    )
) {
}
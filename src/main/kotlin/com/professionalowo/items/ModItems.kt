package com.professionalowo.items

import com.professionalowo.Initializer
import com.professionalowo.items.misc.FilledSoulVialItem
import com.professionalowo.items.misc.SoulVialItem
import com.professionalowo.items.swords.WitherSwordItem
import com.professionalowo.util.modIdentifier
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.SwordItem.createAttributeModifiers
import net.minecraft.item.ToolMaterials
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Rarity


object ModItems : Initializer() {
    override fun initialize() {
        logger.info("Initialized Items")
    }

    val WITHER_SWORD = RegisteredItem.create(
        "wither_sword_item",
    ) {
        WitherSwordItem(
            ToolMaterials.NETHERITE,
            it.fireproof().rarity(Rarity.EPIC).attributeModifiers(
                createAttributeModifiers(ToolMaterials.NETHERITE, 5, -2.4f)
            )
        )
    }

    val VILLAGER_CHARM = RegisteredItem.create("villager_charm_item") { Item(it.maxCount(1)) }
    val SOUL_VIAL = RegisteredItem.create("soul_vial_item") { SoulVialItem(it.maxCount(16)) }
    val FILLED_SOUL_VIAL =
        RegisteredItem.create(
            "filled_soul_vial_item"
        ) { FilledSoulVialItem(it.maxCount(1).rarity(Rarity.UNCOMMON)) }

    val ALTAR_FOCUS = RegisteredItem.create(
        "altar_focus_item"
    ) { Item(it.maxCount(1)) }

    data class RegisteredItem<I : Item>(val key: RegistryKey<Item>, val item: I) : ItemConvertible by item {
        companion object {
            inline fun <I : Item> create(id: String, itemSupplier: (Item.Settings) -> I): RegisteredItem<I> =
                RegistryKey.of(RegistryKeys.ITEM, modIdentifier(id)).let { key ->
                    RegisteredItem(key, Registry.register(Registries.ITEM, key, itemSupplier(Item.Settings())))
                }
        }
    }
}
package com.professionalowo.items

import com.professionalowo.Initializer
import com.professionalowo.items.swords.WitherSwordItem
import com.professionalowo.util.modIdentifier
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys


object ModItems : Initializer() {
    override fun initialize() {
        logger.info("Initialized Items")
    }

    val WITHER_SWORD = createItem(
        "wither_sword_item",
        WitherSwordItem(),
    )

    val ALTAR_FOCUS = createItem(
        "altar_focus_item",
        Item(Item.Settings().maxCount(1))
    )

    data class RegisteredItem(val key: RegistryKey<Item>, val item: Item) : ItemConvertible by item

    private fun register(item: Item, registryKey: RegistryKey<Item>): Item =
        Registry.register(Registries.ITEM, registryKey.value, item)

    private fun createItem(id: String, item: Item): RegisteredItem {
        val key = RegistryKey.of(RegistryKeys.ITEM, modIdentifier(id))
        return RegisteredItem(key, register(item, key))
    }
}
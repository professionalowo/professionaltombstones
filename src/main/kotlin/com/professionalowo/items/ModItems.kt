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

    data class RegisteredItem(val key: RegistryKey<Item>, val item: Item) : ItemConvertible by item

    private fun register(item: Item, registryKey: RegistryKey<Item>): Item {
        // Register the item.
        val registeredItem: Item = Registry.register(Registries.ITEM, registryKey.value, item)

        // Return the registered item!
        return registeredItem
    }

    private fun createItem(id: String, item: Item): RegisteredItem {
        val key = RegistryKey.of(RegistryKeys.ITEM, modIdentifier(id))
        return RegisteredItem(key, register(item, key))
    }
}
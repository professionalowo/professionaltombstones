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

    val WITHER_SWORD = registerItem(
        "wither_sword_item",
        WitherSwordItem(),
    )

    val ALTAR_FOCUS = registerItem(
        "altar_focus_item",
        Item(Item.Settings().maxCount(1))
    )

    data class RegisteredItem<I : Item>(val key: RegistryKey<Item>, val item: I) : ItemConvertible by item

    private fun <I : Item> register(registryKey: RegistryKey<Item>, item: I): I =
        Registry.register(Registries.ITEM, registryKey.value, item)

    private fun <I : Item> registerItem(id: String, item: I): RegisteredItem<I> {
        val key = RegistryKey.of(RegistryKeys.ITEM, modIdentifier(id))
        val registered = register(key, item)
        return RegisteredItem(key, registered)
    }
}
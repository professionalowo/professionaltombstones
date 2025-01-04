package com.professionalowo.creative_tabs

import com.professionalowo.Professionaltombstones
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.util.createLogger
import com.professionalowo.util.modIdentifier
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text

object ModTabs {
    private val logger = createLogger()

    val TOMBSTONE_GROUP = createGroup("tombstone_group") { ItemStack(ModBlocks.TOMBSTONE_BLOCK) }

    fun initialize() {
        TOMBSTONE_GROUP.register(ModBlocks.TOMBSTONE_BLOCK)

        logger.info("Initialized ItemGroups")
    }
}

data class Group(val key: RegistryKey<ItemGroup>, val itemGroup: ItemGroup) {
    fun register(registerFunc: (FabricItemGroupEntries) -> Unit) {
        Registry.register(Registries.ITEM_GROUP, key, itemGroup)
        ItemGroupEvents.modifyEntriesEvent(key).register(registerFunc)
    }

    fun register(vararg itemConvertibles: ItemConvertible) =
        register { group -> itemConvertibles.forEach { group.add(it) } }
}

internal fun createGroup(id: String, iconSupplier: () -> ItemStack): Group {
    val key = RegistryKey.of(Registries.ITEM_GROUP.key, modIdentifier(id))
    val group = FabricItemGroup.builder()
        .icon(iconSupplier)
        .displayName(Text.translatable("itemGroup.${Professionaltombstones.MOD_ID}.$id"))
        .build()

    return Group(key, group)
}
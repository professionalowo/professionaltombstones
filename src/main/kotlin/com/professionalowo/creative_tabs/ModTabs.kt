package com.professionalowo.creative_tabs

import com.professionalowo.Initializer
import com.professionalowo.Professionaltombstones
import com.professionalowo.blocks.ModBlocks
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

object ModTabs : Initializer() {
    val TOMBSTONE_GROUP = createGroup("tombstone_group", ModBlocks.TOMBSTONE_BLOCK)

    override fun initialize() {
        TOMBSTONE_GROUP.register(ModBlocks.TOMBSTONE_BLOCK, ModBlocks.ALTAR_PEDESTAL_BLOCK, ModBlocks.ALTAR_CORE_BLOCK)

        logger.info("Initialized ItemGroups")
    }
}

data class Group(val key: RegistryKey<ItemGroup>, val itemGroup: ItemGroup) {
    fun register(registerFunc: (FabricItemGroupEntries) -> Unit) =
        ItemGroupEvents.modifyEntriesEvent(key).register(registerFunc)

    fun register(vararg itemConvertibles: ItemConvertible) =
        register { group -> itemConvertibles.forEach { group.add(it) } }
}

/**
 * Creates an ItemGroup and RegistryKey<ItemGroup> from the supplied id
 */
fun createGroup(id: String, iconSupplier: () -> ItemStack): Group {
    val key = RegistryKey.of(Registries.ITEM_GROUP.key, modIdentifier(id))
    val group = createItemGroup(id, iconSupplier)
    return Group(key, Registry.register(Registries.ITEM_GROUP, key, group))
}

/**
 * @see createGroup
 */
fun createGroup(id: String, iconItem: ItemConvertible) = createGroup(id) { ItemStack(iconItem) }

/**
 * Simple function to create an ItemGroup with a name and icon
 */
private fun createItemGroup(id: String, iconSupplier: () -> ItemStack): ItemGroup =
    FabricItemGroup.builder()
        .icon(iconSupplier)
        .displayName(Text.translatable(createItemGroupKey(id)))
        .build()

private fun createItemGroupKey(id: String) = "itemGroup.${Professionaltombstones.MOD_ID}.$id"
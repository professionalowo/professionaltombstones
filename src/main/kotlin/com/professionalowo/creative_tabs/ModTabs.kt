package com.professionalowo.creative_tabs

import com.professionalowo.blocks.ModBlocks
import com.professionalowo.util.modIdentifier
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text

object ModTabs {
    val TOMBSTONE_GROUP_KEY: RegistryKey<ItemGroup> =
        RegistryKey.of(Registries.ITEM_GROUP.key, modIdentifier("tombstone_group"))

    val TOMBSTOME_ITEM_GROUP: ItemGroup = FabricItemGroup.builder()
        .icon { ItemStack(ModBlocks.TOMBSTONE_BLOCK.asItem()) }
        .displayName(Text.translatable("itemGroup.tombstones"))
        .build()

    fun initialize() {
        Registry.register(Registries.ITEM_GROUP, TOMBSTONE_GROUP_KEY, TOMBSTOME_ITEM_GROUP)

        ItemGroupEvents.modifyEntriesEvent(TOMBSTONE_GROUP_KEY).register {
            it.add(ModBlocks.TOMBSTONE_BLOCK.asItem())
        }
    }
}
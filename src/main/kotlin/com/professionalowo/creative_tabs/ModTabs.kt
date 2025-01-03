package com.professionalowo.creative_tabs

import com.professionalowo.blocks.ModBlocks
import com.professionalowo.util.createLogger
import com.professionalowo.util.modIdentifier
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.text.Text

object ModTabs {
    private val logger = createLogger()
    val TOMBSTONE_GROUP_KEY: RegistryKey<ItemGroup> =
        RegistryKey.of(Registries.ITEM_GROUP.key, modIdentifier("tombstone_group"))

    val TOMBSTOME_ITEM_GROUP: ItemGroup = FabricItemGroup.builder()
        .icon { ItemStack(ModBlocks.TOMBSTONE_BLOCK) }
        .displayName(Text.translatable("itemGroup.tombstones"))
        .build()

    fun initialize() {
        TOMBSTOME_ITEM_GROUP.registerGroup(TOMBSTONE_GROUP_KEY) {
            it.add(ModBlocks.TOMBSTONE_BLOCK)
        }

        logger.info("Initialized ItemGroups")
    }

    private fun ItemGroup.registerGroup(key: RegistryKey<ItemGroup>, registerFunc: (FabricItemGroupEntries) -> Unit) {
        Registry.register(Registries.ITEM_GROUP, key, this)
        ItemGroupEvents.modifyEntriesEvent(key).register(registerFunc)
    }
}
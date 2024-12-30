package com.professionalowo.blocks

import com.professionalowo.blocks.tombstone.TombstoneBlock
import com.professionalowo.util.modIdentifier
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModBlocks {
    val TOMBSTONE_BLOCK = register(TombstoneBlock(AbstractBlock.Settings.create()), "tombstone_block", true)

    fun initialize() {}

    private fun register(block: Block, name: String, shouldRegisterItem: Boolean): Block {
        val id = modIdentifier(name)

        if (shouldRegisterItem) {
            val blockItem = BlockItem(block, Item.Settings())
            Registry.register(Registries.ITEM, id, blockItem)
        }

        return Registry.register(Registries.BLOCK, id, block)
    }
}
package com.professionalowo.blocks

import com.professionalowo.blocks.tombstone.TombstoneBlock
import com.professionalowo.util.createLogger
import com.professionalowo.util.modIdentifier
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModBlocks {
    private val logger = createLogger()
    val TOMBSTONE_BLOCK = TombstoneBlock(
        AbstractBlock.Settings.create()
            .dropsNothing()
            .hardness(7f)
            .luminance { 2 }
            .mapColor(MapColor.BROWN)
            .pistonBehavior(PistonBehavior.DESTROY)
    ).register("tombstone_block", true)


    fun initialize() {
        logger.info("Initialized Blocks")
    }

    private fun Block.register(
        name: String,
        shouldRegisterItem: Boolean,
        blockItemFactory: ((Block) -> Item) = { block -> BlockItem(block, Item.Settings()) }
    ) = register(name, this, shouldRegisterItem, blockItemFactory)

    private fun register(
        name: String,
        block: Block,
        shouldRegisterItem: Boolean,
        blockItemFactory: (Block) -> Item
    ): Block {
        val id = modIdentifier(name)

        if (shouldRegisterItem)
            Registry.register(Registries.ITEM, id, blockItemFactory(block))


        return Registry.register(Registries.BLOCK, id, block)
    }
}
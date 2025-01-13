package com.professionalowo.blocks

import com.professionalowo.Initializer
import com.professionalowo.blocks.altar.AltarCoreBlock
import com.professionalowo.blocks.altar.AltarPedestalBlock
import com.professionalowo.blocks.tombstone.TombstoneBlock
import com.professionalowo.util.modIdentifier
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.MapColor
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModBlocks : Initializer() {
    val TOMBSTONE_BLOCK = TombstoneBlock(
        AbstractBlock.Settings.create()
            .dropsNothing()
            .hardness(7f)
            .luminance { 2 }
            .mapColor { if (it.get(TombstoneBlock.WATERLOGGED)) MapColor.BLUE else MapColor.BROWN }
            .pistonBehavior(PistonBehavior.DESTROY)
    ).register("tombstone_block", true)

    val ALTAR_CORE_BLOCK =
        AltarCoreBlock(AbstractBlock.Settings.create().hardness(3f).requiresTool()).register("altar_core_block", true)

    val ALTAR_PEDESTAL_BLOCK =
        AltarPedestalBlock(AbstractBlock.Settings.create().hardness(3f).requiresTool()).register(
            "altar_pedestal_block",
            true
        )

    override fun initialize() {
        logger.info("Initialized Blocks")
    }

    private inline fun Block.register(
        name: String,
        shouldRegisterItem: Boolean,
        blockItemFactory: ((Block) -> Item) = { BlockItem(it, Item.Settings()) }
    ) = register(name, this, shouldRegisterItem, blockItemFactory)

    private inline fun register(
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

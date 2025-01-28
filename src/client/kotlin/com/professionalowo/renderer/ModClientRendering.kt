package com.professionalowo.renderer

import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories

object ModClientRendering {
    fun registerRenderers() {
        BlockEntityRendererFactories.register(ModBlockEntities.TOMBSTONE_BLOCK_ENTITY) { TombstoneEntityRenderer(it) }
        BlockEntityRendererFactories.register(ModBlockEntities.ALTAR_PEDESTAL_BLOCK_ENTITY) {
            AbstractAltarBlockEntityRenderer(it)
        }
        BlockEntityRendererFactories.register(ModBlockEntities.ALTAR_CORE_BLOCK_ENTITY) {
            AbstractAltarBlockEntityRenderer(it)
        }
    }
}
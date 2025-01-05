package com.professionalowo

import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.renderer.TombstoneEntityRenderer
import com.professionalowo.util.createLogger
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.Block
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories

object ProfessionaltombstonesClient : Initializer(), ClientModInitializer {
    override fun initialize() {
        RenderLayer.getCutout().putBlocks(ModBlocks.TOMBSTONE_BLOCK)

        BlockEntityRendererFactories.register(ModBlockEntities.TOMBSTONE_BLOCK_ENTITY) { TombstoneEntityRenderer(it) }
        logger.info("Initialized Client for $MOD_ID")
    }

    override fun onInitializeClient() = initialize()

    private fun RenderLayer.putBlocks(vararg blocks: Block) = BlockRenderLayerMap.INSTANCE.putBlocks(this, *blocks)
}
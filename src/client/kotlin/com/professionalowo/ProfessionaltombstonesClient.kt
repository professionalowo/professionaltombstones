package com.professionalowo

import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.networking.ModClientNetworking
import com.professionalowo.renderer.ModClientRendering
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.Block
import net.minecraft.client.render.RenderLayer

object ProfessionaltombstonesClient : Initializer(), ClientModInitializer {
    override fun initialize() {
        RenderLayer.getCutout()
            .putBlocks(ModBlocks.TOMBSTONE_BLOCK, ModBlocks.ALTAR_CORE_BLOCK, ModBlocks.ALTAR_PEDESTAL_BLOCK)

        ModClientRendering.registerRenderers()
        ModClientNetworking.registerS2CPackets()

        logger.info("Initialized Client for $MOD_ID")
    }


    override fun onInitializeClient() = initialize()

    private fun RenderLayer.putBlocks(vararg blocks: Block) = BlockRenderLayerMap.INSTANCE.putBlocks(this, *blocks)
}
package com.professionalowo

import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.util.createLogger
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object ProfessionaltombstonesClient : ClientModInitializer {
    private val logger = createLogger()
    override fun onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOMBSTONE_BLOCK, RenderLayer.getCutout())

        logger.info("Initialized Client for $MOD_ID")
    }
}
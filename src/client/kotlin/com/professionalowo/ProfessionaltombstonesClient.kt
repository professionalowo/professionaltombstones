package com.professionalowo

import com.professionalowo.blocks.ModBlocks
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object ProfessionaltombstonesClient : ClientModInitializer {
    override fun onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOMBSTONE_BLOCK, RenderLayer.getCutout())
    }
}
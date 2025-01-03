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

object ProfessionaltombstonesClient : ClientModInitializer {
    private val logger = createLogger()
    override fun onInitializeClient() {
        putCutouts(ModBlocks.TOMBSTONE_BLOCK)

        BlockEntityRendererFactories.register(ModBlockEntities.TOMBSTONE_BLOCK_ENTITY) { TombstoneEntityRenderer(it) }
        logger.info("Initialized Client for $MOD_ID")
    }

    private fun putCutouts(vararg blocks: Block) =
        putRenderLayer(RenderLayer.getCutout())(blocks)


    private fun putRenderLayer(layer: RenderLayer): (Array<out Block>) -> Unit {
        return fun(blocks: Array<out Block>) {
            BlockRenderLayerMap.INSTANCE.putBlocks(layer, *blocks)
        }
    }
}
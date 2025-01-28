package com.professionalowo

import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.networking.ManaUpdateS2CPayloadHandler
import com.professionalowo.networking.ModClientNetworking
import com.professionalowo.networking.packets.ManaUpdateS2CPayload
import com.professionalowo.renderer.AbstractAltarBlockEntityRenderer
import com.professionalowo.renderer.TombstoneEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.block.Block
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories

object ProfessionaltombstonesClient : Initializer(), ClientModInitializer {
    override fun initialize() {
        RenderLayer.getCutout()
            .putBlocks(ModBlocks.TOMBSTONE_BLOCK, ModBlocks.ALTAR_CORE_BLOCK, ModBlocks.ALTAR_PEDESTAL_BLOCK)

        BlockEntityRendererFactories.register(ModBlockEntities.TOMBSTONE_BLOCK_ENTITY) { TombstoneEntityRenderer(it) }
        BlockEntityRendererFactories.register(ModBlockEntities.ALTAR_PEDESTAL_BLOCK_ENTITY) {
            AbstractAltarBlockEntityRenderer(it)
        }
        BlockEntityRendererFactories.register(ModBlockEntities.ALTAR_CORE_BLOCK_ENTITY) {
            AbstractAltarBlockEntityRenderer(it)
        }
        logger.info("Initialized Client for $MOD_ID")

        ModClientNetworking.registerS2CPackets()
    }


    override fun onInitializeClient() = initialize()

    private fun RenderLayer.putBlocks(vararg blocks: Block) = BlockRenderLayerMap.INSTANCE.putBlocks(this, *blocks)
}
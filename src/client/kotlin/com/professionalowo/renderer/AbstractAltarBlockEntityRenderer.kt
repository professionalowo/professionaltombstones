package com.professionalowo.renderer

import com.professionalowo.blocks.altar.AltarBlockEntity
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.OverlayTexture
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.render.model.json.ModelTransformationMode
import net.minecraft.client.util.math.MatrixStack

@Environment(EnvType.CLIENT)
class AbstractAltarBlockEntityRenderer(private val ctx: BlockEntityRendererFactory.Context) :
    BlockEntityRenderer<AltarBlockEntity> {

    override fun render(
        entity: AltarBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        val stack = entity.item
        val itemRenderer = ctx.itemRenderer

        matrices.push()
        moveModel(matrices)
        itemRenderer.renderItem(
            stack,
            ModelTransformationMode.FIXED,
            light,
            OverlayTexture.DEFAULT_UV,
            matrices,
            vertexConsumers,
            entity.world,
            0
        )
        matrices.pop()
    }

    private fun moveModel(stack: MatrixStack) {
        stack.translate(0.5, 1.0, 0.5)
        stack.scale(0.5f, 0.5f, 0.5f)
    }
}
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
import net.minecraft.util.math.RotationAxis
import kotlin.math.sin

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
        val stack = entity.getStack(0)
        if (stack.isEmpty) return
        val itemRenderer = ctx.itemRenderer

        matrices.push()
        moveModel(entity, matrices)
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

    private fun moveModel(entity: AltarBlockEntity, stack: MatrixStack) {


        val deltaY = sin(entity.ticks.toDouble() * 0.07) * 0.2

        val deltaDeg = entity.ticks % 360 * 1.5

        stack.translate(0.5, 1.2 + deltaY, 0.5)

        stack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(deltaDeg.toFloat()))
        stack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(deltaDeg.toFloat()))
        stack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(deltaDeg.toFloat()))

        stack.scale(0.5f, 0.5f, 0.5f)
    }
}
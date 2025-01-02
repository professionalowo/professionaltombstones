package com.professionalowo

import com.professionalowo.blocks.tombstone.TombstoneBlock
import com.professionalowo.blocks.tombstone.TombstoneBlockEntity
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.RotationAxis
import net.minecraft.util.math.Direction

class TombstoneEntityRenderer(private val ctx: BlockEntityRendererFactory.Context) :
    BlockEntityRenderer<TombstoneBlockEntity> {
    override fun render(
        entity: TombstoneBlockEntity?,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider?,
        light: Int,
        overlay: Int
    ) {
        val text = entity?.name ?: return
        val renderer = ctx.textRenderer
        val width = renderer.getWidth(text)

        val pos = entity.pos

        val blockState = entity.world?.getBlockState(pos) ?: return
        val facingOption = blockState.getOrEmpty(TombstoneBlock.FACING)
        if (facingOption.isEmpty) return
        val facing = facingOption.get()

        matrices.translate(facing.translateX(), 0.65f, facing.translateZ())
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(facing.getRotationAngleY()))
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180f))
        matrices.fit(width)
        matrices.pad(0.8f)

        renderer.draw(
            text,
            -width / 2f,
            -4f,
            0xffffff,
            false,
            matrices.peek().positionMatrix,
            vertexConsumers,
            TextRenderer.TextLayerType.SEE_THROUGH,
            0,
            light
        )
    }

    private fun Direction.getRotationAngleY() = when (this) {
        Direction.NORTH -> 0f
        Direction.EAST -> 270f
        Direction.SOUTH -> 180f
        Direction.WEST -> 90f
        else -> 0f
    }

    private fun Direction.translateX() = when (this) {
        Direction.NORTH, Direction.SOUTH -> 0.5f
        Direction.WEST -> 0.9f
        Direction.EAST -> 0.1f
        else -> 0f
    }


    private fun Direction.translateZ() = when (this) {
        Direction.EAST, Direction.WEST -> 0.5f
        Direction.NORTH -> 0.9f
        Direction.SOUTH -> 0.1f
        else -> 0f
    }

    private fun MatrixStack.fit(width: Int) {
        val scalar = (1f / width)
        scale(scalar, scalar, scalar)
    }

    private fun MatrixStack.pad(padding: Float) = scale(padding, padding, padding)
}
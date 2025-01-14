package com.professionalowo.util

import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.RotationAxis

/**
 * Rotates [this] on all axes by [degrees]
 *
 * @param degrees the angle to rotate
 */
fun MatrixStack.rotateAllAxes(degrees: Float) {
    multiply(RotationAxis.POSITIVE_X.rotationDegrees(degrees))
    multiply(RotationAxis.POSITIVE_Y.rotationDegrees(degrees))
    multiply(RotationAxis.POSITIVE_Z.rotationDegrees(degrees))
}

/**
 * Makes sure [this] fits [targetWidth] into 1 block wide
 *
 * @param targetWidth the width to fit into this
 */
fun MatrixStack.fit(itemWith: Float, targetWidth: Float) {
    val scalar = (targetWidth / itemWith)
    scale(scalar, scalar, scalar)
}

/**
 * Scales [this] with [padding]
 *
 * @param padding the padding
 */
fun MatrixStack.pad(padding: Float) = scale(padding, padding, padding)
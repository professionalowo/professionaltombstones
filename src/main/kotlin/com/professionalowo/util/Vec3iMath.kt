package com.professionalowo.util

import net.minecraft.util.math.Vec3i

operator fun Vec3i.plus(other: Vec3i): Vec3i = this.add(other)

operator fun Vec3i.minus(other: Vec3i): Vec3i = this.subtract(other)

operator fun Vec3i.unaryMinus(): Vec3i = Vec3i(-x, -y, -z)
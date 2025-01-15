package com.professionalowo.util

import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

//math

operator fun Vec3i.plus(other: Vec3i): Vec3i = this.add(other)

operator fun Vec3i.minus(other: Vec3i): Vec3i = this.subtract(other)

operator fun Vec3i.unaryMinus(): Vec3i = Vec3i(-x, -y, -z)


operator fun Vec3d.plus(other: Vec3d): Vec3d = this.add(other)

operator fun Vec3d.minus(other: Vec3d): Vec3d = this.subtract(other)

operator fun Vec3d.unaryMinus(): Vec3d = Vec3d(-x, -y, -z)

//destructuring

operator fun Vec3i.component1() = x

operator fun Vec3i.component2() = y

operator fun Vec3i.component3() = z


operator fun Vec3d.component1() = x

operator fun Vec3d.component2() = y

operator fun Vec3d.component3() = z
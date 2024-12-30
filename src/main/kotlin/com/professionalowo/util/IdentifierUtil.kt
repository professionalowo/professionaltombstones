package com.professionalowo.util

import com.professionalowo.Professionaltombstones.MOD_ID
import net.minecraft.util.Identifier

fun modIdentifier(name:String): Identifier {
    return Identifier.of(MOD_ID, name)
}
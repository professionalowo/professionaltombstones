package com.professionalowo.player_data

import net.minecraft.nbt.NbtCompound

interface IPlayerDataHandler {
    fun getNbtData(): NbtCompound
}
package com.professionalowo.player_data

import net.minecraft.nbt.NbtCompound

interface IPlayerDataHandler {
    fun `professionaltombstone$getNbtData`(): NbtCompound
}
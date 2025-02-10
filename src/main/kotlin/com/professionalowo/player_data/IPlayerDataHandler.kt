package com.professionalowo.player_data

import com.professionalowo.mixin.PlayerEntityMixin
import net.minecraft.nbt.NbtCompound
import net.minecraft.entity.player.PlayerEntity

/**
 * Injected into [PlayerEntity] via [PlayerEntityMixin], used to attach custom nbt data to the player
 */
fun interface IPlayerDataHandler {

    /**
     * Should return a reference to a [NbtCompound], that data can be written to
     * @return the [NbtCompound] holding the custom data
     */
    fun getNbtData(): NbtCompound
}
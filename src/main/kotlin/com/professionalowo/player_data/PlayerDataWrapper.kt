package com.professionalowo.player_data

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound

class PlayerDataWrapper(val player: PlayerEntity) : IPlayerDataHandler {
    val handler: IPlayerDataHandler =
        player as? IPlayerDataHandler ?: throw IllegalStateException("Mixins might not have been initialized")

    override fun getNbtData(): NbtCompound = handler.getNbtData()
}
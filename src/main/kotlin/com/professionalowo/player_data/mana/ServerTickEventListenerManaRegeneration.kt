package com.professionalowo.player_data.mana

import com.professionalowo.player_data.PlayerDataAccessor
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer

class ServerTickEventListenerManaRegeneration : ServerTickEvents.EndTick {
    private var ticks: Int = 0

    override fun onEndTick(server: MinecraftServer) {
        if (ticks % 21 == 0) {
            ticks = 0
            server.playerManager.playerList.map { PlayerDataAccessor(it) }.forEach { regenerateMana(it) }
        }
        ticks++
    }

    private fun regenerateMana(player: PlayerDataAccessor) = player.run {
        mana = minOf(mana + getManaRegenPerSecond(), maxMana)
    }
}
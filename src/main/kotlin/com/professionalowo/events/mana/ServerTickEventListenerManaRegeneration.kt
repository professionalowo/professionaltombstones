package com.professionalowo.events.mana

import com.professionalowo.networking.packets.ManaUpdateS2CPayload
import com.professionalowo.player_data.PlayerDataAccessor
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity

class ServerTickEventListenerManaRegeneration : ServerTickEvents.EndTick {
    private var ticks: Int = 0

    override fun onEndTick(server: MinecraftServer) {
        if (ticks % 21 == 0) {
            ticks = 0
            server.playerManager.playerList.map { PlayerDataAccessor(it) }.forEach { regenerateMana(it) }
        }
        ticks++
    }

    private fun regenerateMana(accessor: PlayerDataAccessor<ServerPlayerEntity>) = accessor.run {
        mana = minOf(mana + getManaRegenPerSecond(), maxMana)
        ServerPlayNetworking.send(accessor.player, ManaUpdateS2CPayload(mana))
    }
}
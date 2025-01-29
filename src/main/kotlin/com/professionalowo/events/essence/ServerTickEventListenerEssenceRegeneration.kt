package com.professionalowo.events.essence

import com.professionalowo.networking.packets.EssenceUpdateS2CPayload
import com.professionalowo.player_data.PlayerDataAccessor
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity

class ServerTickEventListenerEssenceRegeneration : ServerTickEvents.EndTick {
    private var ticks: Int = 0

    override fun onEndTick(server: MinecraftServer) {
        if (ticks % 20 == 0) {
            ticks = 0
            server.playerManager.playerList.map { PlayerDataAccessor(it) }.forEach { regenerateEssence(it) }
        }
        ticks++
    }

    private fun regenerateEssence(accessor: PlayerDataAccessor<ServerPlayerEntity>) = accessor.run {
        if (essence == maxEssence) return@run
        regenerateEssence()
        ServerPlayNetworking.send(player, EssenceUpdateS2CPayload(essence))
    }
}
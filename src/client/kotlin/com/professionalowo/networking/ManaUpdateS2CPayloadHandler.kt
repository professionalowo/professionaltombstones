package com.professionalowo.networking

import com.professionalowo.networking.packets.ManaUpdateS2CPayload
import com.professionalowo.player_data.PlayerDataAccessor
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

class ManaUpdateS2CPayloadHandler : ClientPlayNetworking.PlayPayloadHandler<ManaUpdateS2CPayload> {
    override fun receive(payload: ManaUpdateS2CPayload, context: ClientPlayNetworking.Context) {
        val player = PlayerDataAccessor(context.player())
        player.mana = payload.mana
    }
}
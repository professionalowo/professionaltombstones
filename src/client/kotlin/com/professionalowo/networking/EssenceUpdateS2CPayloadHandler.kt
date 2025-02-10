package com.professionalowo.networking

import com.professionalowo.networking.packets.EssenceUpdateS2CPayload
import com.professionalowo.player_data.PlayerDataAccessor
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

class EssenceUpdateS2CPayloadHandler : ClientPlayNetworking.PlayPayloadHandler<EssenceUpdateS2CPayload> {
    override fun receive(payload: EssenceUpdateS2CPayload, context: ClientPlayNetworking.Context) {
        val player = PlayerDataAccessor(context.player())
        player.essence = payload.updatedValue
    }
}
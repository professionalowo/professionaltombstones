package com.professionalowo.networking

import com.professionalowo.networking.packets.EssenceUpdateS2CPayload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object ModClientNetworking {
    fun registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(EssenceUpdateS2CPayload.ID, EssenceUpdateS2CPayloadHandler());
    }
}
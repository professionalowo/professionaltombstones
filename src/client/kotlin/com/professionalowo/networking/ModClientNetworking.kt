package com.professionalowo.networking

import com.professionalowo.networking.packets.ManaUpdateS2CPayload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object ModClientNetworking {
    fun registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ManaUpdateS2CPayload.ID, ManaUpdateS2CPayloadHandler());
    }
}
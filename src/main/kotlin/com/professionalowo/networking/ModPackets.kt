package com.professionalowo.networking

import com.professionalowo.Initializer
import com.professionalowo.util.modIdentifier
import net.minecraft.network.packet.CustomPayload


object ModPackets : Initializer() {

    override fun initialize() {
        registerPacketTypes()
        registerC2SPackets()
        logger.info("registered s2c packets")
    }

    fun <T : CustomPayload> modPayload(id: String): CustomPayload.Id<T> = CustomPayload.Id(modIdentifier(id))


    private fun registerC2SPackets() {

    }

    private fun registerPacketTypes() {

    }
}
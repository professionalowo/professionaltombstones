package com.professionalowo.networking.packets

import com.professionalowo.networking.ModPackets
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload

class EssenceUpdateS2CPayload(val updatedValue: Int) : CustomPayload {
    companion object {
        val ID: CustomPayload.Id<EssenceUpdateS2CPayload> = ModPackets.modPayload("mana_update")
        val CODEC: PacketCodec<PacketByteBuf, EssenceUpdateS2CPayload> =
            CustomPayload.codecOf(EssenceUpdateS2CPayload::write) {
                EssenceUpdateS2CPayload(it)
            }
    }

    private constructor(buf: PacketByteBuf) : this(buf.readInt())

    fun write(buf: PacketByteBuf) {
        buf.writeInt(updatedValue)
    }

    override fun getId(): CustomPayload.Id<EssenceUpdateS2CPayload> = ID
}
package com.professionalowo.networking.packets

import com.professionalowo.networking.ModPackets
import io.netty.buffer.Unpooled
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload

class ManaUpdateS2CPayload(val mana: Int) : CustomPayload {
    companion object {
        val ID: CustomPayload.Id<ManaUpdateS2CPayload> = ModPackets.modPayload("mana_update")
        val CODEC: PacketCodec<PacketByteBuf, ManaUpdateS2CPayload> =
            CustomPayload.codecOf(ManaUpdateS2CPayload::write) {
                ManaUpdateS2CPayload(it)
            }
    }

    private constructor(buf: PacketByteBuf) : this(buf.readInt())

    fun write(buf: PacketByteBuf) {
        buf.writeInt(mana)
    }

    override fun getId(): CustomPayload.Id<ManaUpdateS2CPayload> = ID
}
package com.professionalowo.util

import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketDecoder
import net.minecraft.network.codec.PacketEncoder

fun <T> PacketEncoder<RegistryByteBuf, T>.encodeList(buf: RegistryByteBuf, list: List<T>) {
    buf.writeVarInt(list.size)
    list.forEach { encode(buf, it) }
}

fun <T> PacketDecoder<RegistryByteBuf, T>.decodeList(buf: RegistryByteBuf): List<T> {
    val size = buf.readVarInt()
    return buildList {
        for (i in 0 until size) {
            add(decode(buf))
        }
    }
}

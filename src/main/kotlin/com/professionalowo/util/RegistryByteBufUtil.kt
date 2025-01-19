package com.professionalowo.util

import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketDecoder
import net.minecraft.network.codec.PacketEncoder

fun <T> RegistryByteBuf.writeList(list: List<T>, encoder: PacketEncoder<RegistryByteBuf, T>) {
    fun encode(value: T) = encoder.encode(this, value)
    writeVarInt(list.size)
    for (i in list) {
        encode(i)
    }
}

fun <T> RegistryByteBuf.readList(decoder: PacketDecoder<RegistryByteBuf, T>): List<T> {
    fun decode(): T = decoder.decode(this)
    val size = readVarInt()
    return buildList {
        for (i in 0 until size) {
            add(decode())
        }
    }
}

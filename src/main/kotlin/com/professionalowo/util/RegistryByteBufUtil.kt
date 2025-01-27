package com.professionalowo.util

import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs


fun <T> PacketCodec<RegistryByteBuf, T>.encodeList(buf: RegistryByteBuf, list: List<T>) =
    collect(PacketCodecs.toList()).encode(buf, list)


fun <T> PacketCodec<RegistryByteBuf, T>.decodeList(buf: RegistryByteBuf): List<T> =
    collect(PacketCodecs.toList()).decode(buf)

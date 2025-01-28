package com.professionalowo.util

import net.minecraft.network.PacketByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs


fun <B, T> PacketCodec<B, T>.encodeList(buf: B, list: List<T>) where B : PacketByteBuf =
    collect(PacketCodecs.toList()).encode(buf, list)


fun <B, T> PacketCodec<B, T>.decodeList(buf: B): List<T> where B : PacketByteBuf =
    collect(PacketCodecs.toList()).decode(buf)

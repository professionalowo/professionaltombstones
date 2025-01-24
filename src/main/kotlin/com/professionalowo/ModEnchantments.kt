package com.professionalowo

import com.professionalowo.util.modIdentifier
import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

object ModEnchantments : Initializer() {
    val SOULBOUND = of("soulbound")

    override fun initialize() = logger.info("Initialising Enchantments")

    fun of(id: String): RegistryKey<Enchantment> = RegistryKey.of(RegistryKeys.ENCHANTMENT, modIdentifier(id))
}
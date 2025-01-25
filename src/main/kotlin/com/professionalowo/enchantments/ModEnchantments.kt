package com.professionalowo.enchantments

import com.professionalowo.Initializer
import com.professionalowo.util.modIdentifier
import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys

object ModEnchantments : Initializer() {

    override fun initialize() = logger.info("Initialising Enchantments")

    fun of(id: String): RegistryKey<Enchantment> = RegistryKey.of(RegistryKeys.ENCHANTMENT, modIdentifier(id))
}
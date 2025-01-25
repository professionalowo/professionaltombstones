package com.professionalowo.generator

import com.professionalowo.enchantments.ModEnchantments.SOULBOUND
import com.professionalowo.Professionaltombstones.MOD_ID
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition
import net.minecraft.component.type.AttributeModifierSlot
import net.minecraft.enchantment.Enchantment
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture


class ModEnchantmentGenerator(output: FabricDataOutput?, registriesFuture: CompletableFuture<WrapperLookup?>?) :
    FabricDynamicRegistryProvider(output, registriesFuture) {

    override fun configure(registries: WrapperLookup, entries: Entries) = entries.run {

    }

    private fun Entries.register(
        key: RegistryKey<Enchantment>,
        builder: Enchantment.Builder,
        vararg resourceConditions: ResourceCondition
    ) {
        add(key, builder.build(key.value), *resourceConditions)
    }

    override fun getName(): String {
        return "${MOD_ID}EnchantmentGenerator"
    }
}
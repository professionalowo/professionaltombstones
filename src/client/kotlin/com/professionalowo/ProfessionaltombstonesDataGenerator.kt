package com.professionalowo

import com.professionalowo.generator.ModBlockTagProvider
import com.professionalowo.generator.ModEnchantmentGenerator
import com.professionalowo.generator.ModEnglishLanguageProvider
import com.professionalowo.generator.ModItemTagProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object ProfessionaltombstonesDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        val pack = generator.createPack()
        pack.addProvider { a, b -> ModBlockTagProvider(a, b) }
        pack.addProvider { a, b -> ModItemTagProvider(a, b) }
        pack.addProvider { a, b -> ModEnglishLanguageProvider(a, b) }
        pack.addProvider { a, b -> ModEnchantmentGenerator(a, b) }
    }
}
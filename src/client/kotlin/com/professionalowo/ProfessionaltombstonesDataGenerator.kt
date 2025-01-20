package com.professionalowo

import com.professionalowo.generator.ProfessionalTombstonesBlockTagProvider
import com.professionalowo.generator.ProfessionalTombstonesItemTagProvider
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object ProfessionaltombstonesDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(generator: FabricDataGenerator) {
        val pack = generator.createPack()
        pack.addProvider { a, b -> ProfessionalTombstonesBlockTagProvider(a, b) }
        pack.addProvider { a, b -> ProfessionalTombstonesItemTagProvider(a, b) }
    }
}
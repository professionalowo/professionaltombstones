package com.professionalowo.generator

import com.professionalowo.Professionaltombstones.MOD_ID
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import kotlin.jvm.optionals.getOrNull

class ModEnglishLanguageProvider(output: FabricDataOutput, future: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    FabricLanguageProvider(output, "en_us", future) {
    override fun generateTranslations(lookup: RegistryWrapper.WrapperLookup, builder: TranslationBuilder) {
        getExistingPath()?.let {
            builder.add(it)
        }
    }

    private fun getExistingPath(): Path? =
        dataOutput.modContainer.findPath("assets/$MOD_ID/lang/en_us.existing.json").getOrNull()

}
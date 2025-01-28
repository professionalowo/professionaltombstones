package com.professionalowo.generator

import com.professionalowo.items.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.ItemTags
import java.util.concurrent.CompletableFuture

class ModItemTagProvider(
    output: FabricDataOutput,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>
) : ItemTagProvider(output, registriesFuture, ModBlockTagProvider(output, registriesFuture)) {
    override fun configure(lookup: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(ItemTags.SWORDS)
            .add(ModItems.WITHER_SWORD.item)
    }
}
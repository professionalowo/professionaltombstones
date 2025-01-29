package com.professionalowo

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.creative_tabs.ModTabs
import com.professionalowo.enchantments.ModEnchantments
import com.professionalowo.events.ModEventListeners
import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.items.ModItems
import com.professionalowo.networking.ModPackets
import com.professionalowo.recipes.ModRecipeSerializers
import com.professionalowo.recipes.ModRecipieTypes
import com.professionalowo.sound.ModSoundEvents
import net.fabricmc.api.ModInitializer

object Professionaltombstones : Initializer(), ModInitializer {
    const val MOD_ID = "professionaltombstones"

    override fun initialize() =
        arrayOf(
            ModPackets,
            ModRecipieTypes,
            ModRecipeSerializers,
            ModSoundEvents,
            ModGameRules,
            ModTabs,
            ModBlockEntities,
            ModEnchantments,
            ModBlocks,
            ModItems,
            ModEventListeners,
        ).forEach { it.initialize() }
            .also { logger.info("Initialized $MOD_ID") }


    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution
    override fun onInitialize() {
        initialize()
    }
}


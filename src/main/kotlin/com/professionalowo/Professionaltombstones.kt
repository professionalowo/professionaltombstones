package com.professionalowo

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.creative_tabs.ModTabs
import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.items.ModItems
import com.professionalowo.sound.ModSoundEvents
import net.fabricmc.api.ModInitializer

object Professionaltombstones : Initializer(), ModInitializer {
    const val MOD_ID = "professionaltombstones"

    override fun initialize() =
        arrayOf(
            ModSoundEvents,
            ModGameRules,
            ModTabs,
            ModBlockEntities,
            ModBlocks,
            ModItems,
        ).forEach { it.initialize() }
            .also { logger.info("Initialized $MOD_ID") }


    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution
    override fun onInitialize() = initialize()
}


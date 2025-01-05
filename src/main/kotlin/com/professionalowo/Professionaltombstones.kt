package com.professionalowo

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.creative_tabs.ModTabs
import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.items.ModItems
import com.professionalowo.sound.ModSoundEvents
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Professionaltombstones : Initializer(), ModInitializer {
    const val MOD_ID = "professionaltombstones"

    override fun initialize() {
        ModSoundEvents.initialize()
        ModGameRules.initialize()
        ModTabs.initialize()
        ModBlockEntities.initialize()
        ModBlocks.initialize()
        ModItems.initialize()

        logger.info("Initialized $MOD_ID")
    }

    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution
    override fun onInitialize() = initialize()
}


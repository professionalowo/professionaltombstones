package com.professionalowo

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.creative_tabs.ModTabs
import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.items.ModItems
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object Professionaltombstones : ModInitializer {
    const val MOD_ID = "professionaltombstones"
    private val logger = LoggerFactory.getLogger(MOD_ID)


    override fun onInitialize() {
        ModGameRules.initialize()
        ModTabs.initialize()
        ModBlockEntities.initialize()
        ModBlocks.initialize()
        ModItems.initialize()

        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution
        logger.info("Initialized $MOD_ID")
    }
}


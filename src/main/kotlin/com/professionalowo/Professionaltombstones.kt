package com.professionalowo

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.creative_tabs.ModTabs
import com.professionalowo.enchantments.ModEnchantments
import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.items.ModItems
import com.professionalowo.player_data.PlayerDataAccessor
import com.professionalowo.player_data.mana.ServerTickEventListenerManaRegeneration
import com.professionalowo.recipes.ModRecipeSerializers
import com.professionalowo.recipes.ModRecipieTypes
import com.professionalowo.sound.ModSoundEvents
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object Professionaltombstones : Initializer(), ModInitializer {
    const val MOD_ID = "professionaltombstones"

    override fun initialize() =
        arrayOf(
            ModRecipieTypes,
            ModRecipeSerializers,
            ModSoundEvents,
            ModGameRules,
            ModTabs,
            ModBlockEntities,
            ModEnchantments,
            ModBlocks,
            ModItems,
        ).forEach { it.initialize() }
            .also { logger.info("Initialized $MOD_ID") }


    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution
    override fun onInitialize() {
        initialize()

        ServerTickEvents.END_SERVER_TICK.register(ServerTickEventListenerManaRegeneration())
    }
}


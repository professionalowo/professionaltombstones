package com.professionalowo.gamerules

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.minecraft.world.GameRules

object ModGameRules {
    val SPAWN_GRAVESTONE: GameRules.Key<GameRules.BooleanRule> = GameRuleRegistry.register(
        "spawnGravestone",
        GameRules.Category.PLAYER,
        GameRuleFactory.createBooleanRule(true)
    )

    /**
     * Does nothing only static initialisation
     */
    fun initialize(){}
}
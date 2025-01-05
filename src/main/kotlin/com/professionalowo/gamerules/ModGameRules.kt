package com.professionalowo.gamerules

import com.professionalowo.Initializer
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.minecraft.world.GameRules

object ModGameRules : Initializer() {
    val SPAWN_GRAVESTONE: GameRules.Key<GameRules.BooleanRule> = GameRuleRegistry.register(
        "spawnGravestone",
        GameRules.Category.PLAYER,
        GameRuleFactory.createBooleanRule(true)
    )

    /**
     * Does nothing only static initialisation
     */
    override fun initialize() {
        logger.info("Initialized GameRules")
    }
}

/**
 * Checks if all gamerule pairs are set to the right value
 *
 * @param pairs the gamerules and their values
 * @return true if all keys are set to the right value
 */
fun GameRules.allGamerules(vararg pairs: Pair<GameRules.Key<GameRules.BooleanRule>, Boolean>) =
    pairs.all { getBoolean(it.first) == it.second }
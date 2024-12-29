package com.professionalowo.util

import net.minecraft.inventory.Inventory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.GameRules
import net.minecraft.world.World

/**
 * Get the inventory from the BlockEnitity at pos
 * @param pos the BlockPos of the BlockEntity
 * @return the Inventory of the BlockEntity, if available
 */
fun World.getBlockInventory(pos: BlockPos) =
    getBlockEntity(pos) as? Inventory


private typealias KeyToBooleanPair
        = Pair<GameRules.Key<GameRules.BooleanRule>, Boolean>

/**
 * Checks if all gamerule pairs are set to the right value
 *
 * @param pairs the gamerules and their values
 * @return true if all keys are set to the right value
 */
fun World.allGamerules(vararg pairs: KeyToBooleanPair) =
    pairs.all { gameRules.getBoolean(it.first) == it.second }

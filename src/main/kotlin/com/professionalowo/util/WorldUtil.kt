package com.professionalowo.util

import net.minecraft.inventory.Inventory
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * Get the inventory from the BlockEntity at pos
 * @param pos the BlockPos of the BlockEntity
 * @return the Inventory of the BlockEntity, if available
 */
fun World.getBlockInventory(pos: BlockPos) =
    getBlockEntity(pos) as? Inventory

package com.professionalowo.util

import net.minecraft.inventory.Inventory

/**
 * Transfers the contents of this inventory to other, while clearing this, other is overwritten
 * @param other the Inventory that recieves the items
 * @see Inventory
 */
fun Inventory.transferTo(other: Inventory) =
    //fill other inventory with copy
    itemsCopy().withIndex()
        .take(other.size())
        .forEach { (index, stack) -> other.setStack(index, stack) }
        .also { clear() }


/**
 * @return a copy of each ItemStack in the Inventory
 */
fun Inventory.itemsCopy() = (0 until size())
    .mapNotNull { getStack(it) }
    .filter { !it.isEmpty }
    .map { it.copy()!! }

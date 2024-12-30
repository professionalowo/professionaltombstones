package com.professionalowo.util

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

/**
 * Transfers the contents of this inventory to other, while clearing this
 * @param other the Inventory that recieves the items
 * @see Inventory
 */
fun Inventory.transferTo(other: Inventory) {
    //fill other inventory with copy
    for ((index, stack) in itemsCopy().withIndex()) {
        if (index < other.size()) {
            other.setStack(index, stack)
        } else {
            break
        }
    }
    //clear this
    clear()
}

/**
 * @return a copy of each ItemStack in the Inventory
 */
fun Inventory.itemsCopy() = (0 until size())
    .map { getStack(it) ?: ItemStack.EMPTY!! }
    .filter { !it.isEmpty }
    .map { it.copy() }

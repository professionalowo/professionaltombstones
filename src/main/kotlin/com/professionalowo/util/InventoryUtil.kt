package com.professionalowo.util

import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

/**
 * Transfers the contents of this inventory to other, while clearing this
 * @param other the Inventory that recieves the items
 * @see Inventory
 */
fun Inventory.transferTo(other: Inventory) {
    val itemCopies = mutableListOf<ItemStack>()

    //clear this list and make copy
    for (i in 0 until this.size()) {
        val itemStack = this.getStack(i)
        if (itemStack.isEmpty) {
            continue
        }
        itemCopies.add(itemStack.copy())
    }
    this.clear()
    //fill other inventory with copy
    for ((index, stack) in itemCopies.withIndex()) {
        if (index < other.size()) {
            other.setStack(index, stack)
        }
    }
}
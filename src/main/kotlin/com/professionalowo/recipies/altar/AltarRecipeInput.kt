package com.professionalowo.recipies.altar

import net.minecraft.item.ItemStack
import net.minecraft.recipe.input.RecipeInput

data class AltarRecipeInput(
    val core: ItemStack,
    val first: ItemStack,
    val second: ItemStack,
    val third: ItemStack,
    val fourth: ItemStack,
    val fifth: ItemStack,
    val sixth: ItemStack,
    val seventh: ItemStack,
    val eighth: ItemStack,
    val ninth: ItemStack,
) : RecipeInput {
    private fun inputs(): Array<ItemStack> =
        arrayOf(core, first, second, third, fourth, fifth, sixth, seventh, eighth, ninth)

    override fun getStackInSlot(slot: Int): ItemStack = when (slot) {
        0 -> core
        1 -> first
        2 -> second
        3 -> third
        4 -> fourth
        5 -> fifth
        6 -> sixth
        7 -> seventh
        8 -> eighth
        9 -> ninth
        else -> throw IllegalArgumentException("Recipe does not contain slot $slot")
    }

    override fun getSize(): Int = 10

    override fun isEmpty(): Boolean =
        inputs().all { isEmpty() }
}
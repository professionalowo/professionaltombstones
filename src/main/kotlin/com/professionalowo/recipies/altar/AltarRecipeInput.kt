package com.professionalowo.recipies.altar

import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeMatcher
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
    val tenth: ItemStack,
    val eleventh: ItemStack,
    val twelfth: ItemStack,
) : RecipeInput {

    val matcher: RecipeMatcher = RecipeMatcher()

    init {
        inputs().forEach { matcher.addInput(it) }
    }

    companion object {
        fun ofList(core: ItemStack, pedestals: List<ItemStack>): AltarRecipeInput {
            if (pedestals.size != 12) throw UnsupportedOperationException("There have to be 9 ItemStacks in the list, there are ${pedestals.size}")

            return AltarRecipeInput(core, pedestals)
        }
    }

    private constructor(core: ItemStack, pedestals: List<ItemStack>) : this(
        core,
        pedestals[0],
        pedestals[1],
        pedestals[2],
        pedestals[3],
        pedestals[4],
        pedestals[5],
        pedestals[6],
        pedestals[7],
        pedestals[8],
        pedestals[9],
        pedestals[10],
        pedestals[11],
    )

    private fun inputs(): Array<ItemStack> =
        arrayOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth)

    override fun getStackInSlot(slot: Int): ItemStack = when (slot) {
        0 -> core
        in 1..11 -> inputs()[slot]
        else -> throw IllegalArgumentException("Recipe does not contain slot $slot")
    }

    override fun getSize(): Int = 13

    override fun isEmpty(): Boolean =
        inputs().all { it.isEmpty }
}
package com.professionalowo.recipies.altar

import com.professionalowo.blocks.ModBlocks
import com.professionalowo.recipies.ModRecipieTypes
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType

interface AltarRecipe : Recipe<AltarRecipeInput> {

    override fun getType(): RecipeType<*> = ModRecipieTypes.ALTAR

    override fun createIcon(): ItemStack = ItemStack(ModBlocks.ALTAR_CORE_BLOCK)

    override fun fits(width: Int, height: Int): Boolean = width >= 10 && height >= 1

    fun testCore(stack: ItemStack): Boolean

    fun testPedestals(items: List<ItemStack>): Boolean
}
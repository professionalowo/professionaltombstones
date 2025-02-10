package com.professionalowo.util.recipes

import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.RecipeManager
import net.minecraft.recipe.input.SingleStackRecipeInput
import net.minecraft.world.World
import java.util.*


class AcceleratedAbstractCookingRecipeMatchGetter(
    private val matcher: RecipeManager.MatchGetter<SingleStackRecipeInput, out AbstractCookingRecipe>,
    private val acceleration: () -> Float
) :
    RecipeManager.MatchGetter<SingleStackRecipeInput, AbstractCookingRecipe> {
    override fun getFirstMatch(
        input: SingleStackRecipeInput?,
        world: World?
    ): Optional<RecipeEntry<AbstractCookingRecipe>> =
        matcher.getFirstMatch(input, world).map {
            RecipeEntry(it.id, it.value.accelerate(acceleration()))
        }
}

package com.professionalowo.util

import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.RecipeManager
import net.minecraft.recipe.SmeltingRecipe
import net.minecraft.recipe.input.SingleStackRecipeInput
import net.minecraft.world.World
import java.util.*


class FurnaceRecipeGetterAccelerated(
    private val matcher: RecipeManager.MatchGetter<SingleStackRecipeInput, out AbstractCookingRecipe>,
    private val acceleration: Float
) :
    RecipeManager.MatchGetter<SingleStackRecipeInput, AbstractCookingRecipe> {
    override fun getFirstMatch(
        input: SingleStackRecipeInput?,
        world: World?
    ): Optional<RecipeEntry<AbstractCookingRecipe>> {
        val inner = matcher.getFirstMatch(input, world);
        return inner.map {
            val recipe = it.value;
            val discounted = recipe.run {
                SmeltingRecipe(
                    group,
                    category,
                    ingredients.first(),
                    getResult(null),
                    experience,
                    Math.round(cookingTime / acceleration)
                );
            }
            RecipeEntry<AbstractCookingRecipe>(it.id, discounted)
        }
    }
}

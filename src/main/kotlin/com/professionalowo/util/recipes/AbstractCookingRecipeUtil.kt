package com.professionalowo.util.recipes

import net.minecraft.recipe.AbstractCookingRecipe
import net.minecraft.recipe.SmeltingRecipe
import kotlin.math.roundToInt

fun AbstractCookingRecipe.accelerate(factor: Float): AbstractCookingRecipe = SmeltingRecipe(
    group,
    category,
    ingredients.first(),
    getResult(null),
    experience,
    (cookingTime / factor).roundToInt()
)

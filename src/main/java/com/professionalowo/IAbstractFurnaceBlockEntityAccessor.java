package com.professionalowo;

import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;

public interface IAbstractFurnaceBlockEntityAccessor {
    void setMatchGetter(RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> matchGetter);
    RecipeManager.MatchGetter<SingleStackRecipeInput, ? extends AbstractCookingRecipe> getMatchGetter();
}

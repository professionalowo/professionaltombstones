package com.professionalowo.recipes

import com.professionalowo.Initializer
import com.professionalowo.recipes.altar.AltarRecipe
import com.professionalowo.util.modIdentifier
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModRecipieTypes : Initializer() {

    val ALTAR: RecipeType<AltarRecipe> = register("crafting_altar")

    override fun initialize() = logger.info("Initialized Recipe Types")


    fun register(id: String): AltarRecipeType {
        return Registry.register(Registries.RECIPE_TYPE, modIdentifier(id), AltarRecipeType(id))
    }

    class AltarRecipeType(private val id: String) : RecipeType<AltarRecipe> {
        override fun toString(): String = id
    }
}
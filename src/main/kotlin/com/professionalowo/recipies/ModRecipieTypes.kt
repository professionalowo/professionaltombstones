package com.professionalowo.recipies

import com.professionalowo.Initializer
import com.professionalowo.recipies.altar.AltarRecipe
import com.professionalowo.util.modIdentifier
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModRecipieTypes : Initializer() {

    val ALTAR: RecipeType<AltarRecipe> = register("altar")

    override fun initialize() = logger.info("Initialized Recipe Types")

    fun <T : Recipe<*>?> register(id: String): RecipeType<T> {
        return Registry.register(Registries.RECIPE_TYPE, modIdentifier(id), object : RecipeType<T> {
            override fun toString(): String {
                return id
            }
        }) as RecipeType<T>
    }
}
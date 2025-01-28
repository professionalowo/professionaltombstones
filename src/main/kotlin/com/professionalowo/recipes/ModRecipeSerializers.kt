package com.professionalowo.recipes

import com.professionalowo.Initializer
import com.professionalowo.recipes.altar.AltarRecipe
import com.professionalowo.util.modIdentifier
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModRecipeSerializers : Initializer() {
    val ALTAR = register("crafting_altar") { AltarRecipe.Serializer() }

    override fun initialize() = logger.info("Initialized Recipe Serializers")

    private inline fun <S : RecipeSerializer<T>, T : Recipe<*>> register(
        id: String,
        serializerProvider: () -> S
    ): S = modIdentifier(id).let { Registry.register(Registries.RECIPE_SERIALIZER, it, serializerProvider()) }
}
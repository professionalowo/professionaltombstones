package com.professionalowo.recipes.altar

import com.mojang.serialization.DataResult
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.recipes.ModRecipeSerializers
import com.professionalowo.recipes.ModRecipieTypes
import com.professionalowo.util.decodeList
import com.professionalowo.util.encodeList
import net.minecraft.item.ItemStack
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.recipe.*
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class AltarRecipe(
    private val coreIngredient: Ingredient,
    private val ingredients: DefaultedList<Ingredient>,
    private val result: ItemStack,
) :
    Recipe<AltarRecipeInput> {
    companion object {
        const val MAX_INGREDIENTS = 12
        fun of(coreIngredient: Ingredient, ingredients: Iterable<Ingredient>, result: ItemStack): AltarRecipe {
            val defaultedList: DefaultedList<Ingredient> = DefaultedList.ofSize(MAX_INGREDIENTS, Ingredient.EMPTY)
            ingredients.take(MAX_INGREDIENTS).forEachIndexed { index, ingredient -> defaultedList[index] = ingredient }
            return AltarRecipe(coreIngredient, defaultedList, result)
        }

        fun of(coreIngredient: Ingredient, vararg ingredients: Ingredient, result: ItemStack): AltarRecipe =
            of(coreIngredient, ingredients.asIterable(), result)

    }

    override fun getType(): RecipeType<*> = ModRecipieTypes.ALTAR

    override fun createIcon(): ItemStack = ItemStack(ModBlocks.ALTAR_CORE_BLOCK)
    override fun getSerializer(): RecipeSerializer<*> = ModRecipeSerializers.ALTAR

    override fun matches(input: AltarRecipeInput?, world: World?): Boolean =
        input != null && world != null && coreMatches(input) && testEmptyPedestals(input) && input.matcher.match(
            this,
            null
        )

    private fun coreMatches(input: AltarRecipeInput): Boolean = coreIngredient.test(input.core)

    private fun testEmptyPedestals(input: AltarRecipeInput): Boolean {
        val emptyIngredients = ingredients.count { it.isEmpty }
        val emptyInput = input.inputs.count { it.isEmpty }
        return emptyInput == emptyIngredients
    }


    override fun craft(input: AltarRecipeInput, lookup: RegistryWrapper.WrapperLookup?): ItemStack = result.copy()

    override fun fits(width: Int, height: Int): Boolean = width >= 13 && height >= 1
    override fun getResult(registriesLookup: RegistryWrapper.WrapperLookup?): ItemStack = result

    override fun getIngredients(): DefaultedList<Ingredient> =
        DefaultedList.copyOf(Ingredient.EMPTY, *ingredients.toTypedArray())

    class Serializer : RecipeSerializer<AltarRecipe> {
        companion object {
            val CODEC: MapCodec<AltarRecipe> = RecordCodecBuilder.mapCodec { builder ->
                builder.group(
                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("core").forGetter { it.coreIngredient },
                    Ingredient.DISALLOW_EMPTY_CODEC.listOf()
                        .fieldOf("ingredients")
                        .flatXmap({ ingredients ->
                            val readIngredients: Array<Ingredient> =
                                ingredients.filter { !it.isEmpty }.toTypedArray()
                            if (readIngredients.size > MAX_INGREDIENTS) return@flatXmap DataResult.error { "Too many ingredients for altar recipe" }

                            DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, *readIngredients))
                        }, {
                            DataResult.success(it)
                        }).forGetter { it.ingredients },
                    ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf("result").forGetter { it.result }
                ).apply(builder) { core, ingredients, result -> of(core, ingredients, result) }
            }

            val PACKET_CODEC: PacketCodec<RegistryByteBuf, AltarRecipe> = PacketCodec.ofStatic(::write, ::read)

            fun write(buf: RegistryByteBuf, recipe: AltarRecipe) = recipe.run {
                //core
                Ingredient.PACKET_CODEC.encode(buf, coreIngredient)

                //ingredients
                Ingredient.PACKET_CODEC.encodeList(buf, ingredients)

                //result
                ItemStack.PACKET_CODEC.encode(buf, result)
            }

            fun read(buf: RegistryByteBuf): AltarRecipe {
                //core
                val coreIngredient = Ingredient.PACKET_CODEC.decode(buf)

                //ingredients
                val ingredients = Ingredient.PACKET_CODEC.decodeList(buf)

                //result
                val result = ItemStack.PACKET_CODEC.decode(buf)

                return of(coreIngredient, ingredients, result)
            }
        }

        override fun codec(): MapCodec<AltarRecipe> = CODEC

        override fun packetCodec(): PacketCodec<RegistryByteBuf, AltarRecipe> = PACKET_CODEC

    }

}
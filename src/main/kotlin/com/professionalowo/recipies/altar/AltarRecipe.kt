package com.professionalowo.recipies.altar

import com.mojang.serialization.DataResult
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.recipies.ModRecipeSerializers
import com.professionalowo.recipies.ModRecipieTypes
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

    override fun getType(): RecipeType<*> = ModRecipieTypes.ALTAR

    override fun createIcon(): ItemStack = ItemStack(ModBlocks.ALTAR_CORE_BLOCK)
    override fun getSerializer(): RecipeSerializer<*> = ModRecipeSerializers.ALTAR

    override fun matches(input: AltarRecipeInput?, world: World?): Boolean =
        input != null && world != null && coreMatches(input) && input.matcher.match(this, null)

    private fun coreMatches(input: AltarRecipeInput): Boolean {
        return coreIngredient.test(input.core)
    }

    override fun craft(input: AltarRecipeInput, lookup: RegistryWrapper.WrapperLookup): ItemStack = result.copy()

    override fun fits(width: Int, height: Int): Boolean = width >= 10 && height >= 1
    override fun getResult(registriesLookup: RegistryWrapper.WrapperLookup?): ItemStack = result

    override fun getIngredients(): DefaultedList<Ingredient> =
        DefaultedList.copyOf(Ingredient.EMPTY, *ingredients.toTypedArray())

    class Serializer : RecipeSerializer<AltarRecipe> {
        companion object {
            private val CODEC: MapCodec<AltarRecipe> = RecordCodecBuilder.mapCodec {
                it.group(
                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("core").forGetter { recipe -> recipe.coreIngredient },
                    Ingredient.DISALLOW_EMPTY_CODEC.listOf()
                        .fieldOf("ingredients")
                        .flatXmap<DefaultedList<Ingredient>>({ ingredients ->
                            val readIngredients: List<Ingredient> =
                                ingredients.filter { ingredient -> !ingredient.isEmpty }
                            if (readIngredients.size > 9) return@flatXmap DataResult.error { "Too many ingredients for altar recipe" }

                            DataResult.success(
                                DefaultedList.copyOf(
                                    Ingredient.EMPTY,
                                    *readIngredients.toTypedArray()
                                )
                            )
                        }, { data ->
                            DataResult.success(data)
                        }).forGetter { recipe -> recipe.ingredients },
                    ItemStack.VALIDATED_UNCOUNTED_CODEC.fieldOf("result").forGetter { recipe -> recipe.result }
                ).apply(it) { core, ingredients, result -> AltarRecipe(core, ingredients, result) }
            }

            val PACKET_CODEC: PacketCodec<RegistryByteBuf, AltarRecipe> = PacketCodec.ofStatic(::write, ::read)

            fun write(buf: RegistryByteBuf, recipe: AltarRecipe) {
                //core
                Ingredient.PACKET_CODEC.encode(buf, recipe.coreIngredient)

                //ingredients
                buf.writeVarInt(recipe.ingredients.size)
                for (ingredient in recipe.ingredients) {
                    Ingredient.PACKET_CODEC.encode(buf, ingredient)
                }

                //result
                ItemStack.PACKET_CODEC.encode(buf, recipe.result)
            }

            fun read(buf: RegistryByteBuf): AltarRecipe {
                //core
                val core = Ingredient.PACKET_CODEC.decode(buf)

                //ingredients
                val length = buf.readVarInt()
                val ingredients = DefaultedList.ofSize(9, Ingredient.EMPTY)
                for (i in (0 until length)) {
                    ingredients[i] = Ingredient.PACKET_CODEC.decode(buf)
                }

                //result
                val result = ItemStack.PACKET_CODEC.decode(buf)

                return AltarRecipe(core, ingredients, result)
            }
        }

        override fun codec(): MapCodec<AltarRecipe> = CODEC

        override fun packetCodec(): PacketCodec<RegistryByteBuf, AltarRecipe> = PACKET_CODEC

    }

}
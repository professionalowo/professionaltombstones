package com.professionalowo.blocks.altar

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.recipies.ModRecipieTypes
import com.professionalowo.recipies.altar.AltarRecipe
import com.professionalowo.recipies.altar.AltarRecipeInput
import net.minecraft.block.BlockState
import net.minecraft.recipe.RecipeManager
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class AltarCoreBlockEntity(pos: BlockPos, state: BlockState) :
    AbstractAltarBlockEntity(ModBlockEntities.ALTAR_CORE_BLOCK_ENTITY, pos, state) {

    private val matchGetter: RecipeManager.MatchGetter<AltarRecipeInput, AltarRecipe> =
        RecipeManager.createCachedMatchGetter(ModRecipieTypes.ALTAR)

    override fun tick(world: World, pos: BlockPos, state: BlockState) {
        super.tick(world, pos, state)
        val input = getRecipeInput(world, pos)
        val recipeEntry = input?.let { matchGetter.getFirstMatch(it, world).orElse(null) }
    }

    private fun getRecipeInput(world: World, pos: BlockPos): AltarRecipeInput? =
        if (AltarCoreBlock.hasFullPedestals(world, pos)) {
            AltarCoreBlock.getPedestalBlockEntities(world, pos)
                .map { it.item }.take(9).runCatching {
                    AltarRecipeInput.ofList(item, this)
                }.getOrNull()

        } else null
}

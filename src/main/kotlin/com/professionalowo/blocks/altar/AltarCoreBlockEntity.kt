package com.professionalowo.blocks.altar

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.recipies.ModRecipieTypes
import com.professionalowo.recipies.altar.AltarRecipe
import com.professionalowo.recipies.altar.AltarRecipeInput
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.recipe.RecipeManager
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent
import kotlin.jvm.optionals.getOrNull

class AltarCoreBlockEntity(pos: BlockPos, state: BlockState) :
    AbstractAltarBlockEntity(ModBlockEntities.ALTAR_CORE_BLOCK_ENTITY, pos, state) {

    private val matchGetter: RecipeManager.MatchGetter<AltarRecipeInput, AltarRecipe> =
        RecipeManager.createCachedMatchGetter(ModRecipieTypes.ALTAR)


    fun craft(world: World, pos: BlockPos, state: BlockState): Boolean {
        val input = getRecipeInput(world, pos)
        val recipeEntry = input?.let { matchGetter.getFirstMatch(it, world).getOrNull() } ?: return false
        if (world.isClient) return true
        val result = recipeEntry.value.craft(input, world.registryManager)
        item = result.copy()
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state))
        world.updateListeners(pos, state, state, Block.NOTIFY_ALL)
        markDirty()
        return true
    }

    fun consumePedestals(world: World, pos: BlockPos) =
        AltarCoreBlock.getPedestalBlockEntities(world, pos).forEach { it.consumeItem(world) }

    private fun getRecipeInput(world: World, pos: BlockPos): AltarRecipeInput? =
        if (AltarCoreBlock.hasFullPedestals(world, pos)) {
            AltarCoreBlock.getPedestalBlockEntities(world, pos)
                .map { it.item }.take(12).runCatching {
                    AltarRecipeInput.ofList(item, this)
                }.getOrNull()

        } else null
}

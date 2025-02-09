package com.professionalowo.blocks.furnace

import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.block.BlockState
import net.minecraft.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.recipe.RecipeType
import net.minecraft.screen.FurnaceScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos

class AlchemicalFurnaceBlockEntity(pos: BlockPos, state: BlockState?) :
    AbstractFurnaceBlockEntity(ModBlockEntities.ALCHEMICAL_FURNACE_BLOCK_ENTITY, pos, state, RecipeType.SMELTING) {
    override fun getContainerName(): Text = Text.translatable("container.alchemical_furnace")

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory?): ScreenHandler =
        FurnaceScreenHandler(syncId, playerInventory, this, propertyDelegate)
}
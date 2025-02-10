package com.professionalowo.blocks.furnace

import com.professionalowo.IAbstractFurnaceBlockEntityAccessor
import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.util.FurnaceRecipeGetterAccelerated
import net.minecraft.block.BlockState
import net.minecraft.block.entity.AbstractFurnaceBlockEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.*
import net.minecraft.screen.FurnaceScreenHandler
import net.minecraft.screen.ScreenHandler
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos


class AlchemicalFurnaceBlockEntity(pos: BlockPos, state: BlockState?) :
    AbstractFurnaceBlockEntity(ModBlockEntities.ALCHEMICAL_FURNACE_BLOCK_ENTITY, pos, state, RecipeType.SMELTING) {

    init {
        val acc = this as? IAbstractFurnaceBlockEntityAccessor
            ?: throw IllegalStateException("Mixins might not have been initialized")
        acc.setMatchGetter(FurnaceRecipeGetterAccelerated(acc.matchGetter) { 1.5f })
    }

    override fun getContainerName(): Text = Text.translatable("container.alchemical_furnace")

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory?): ScreenHandler =
        FurnaceScreenHandler(syncId, playerInventory, this, propertyDelegate)

    override fun getFuelTime(fuel: ItemStack?): Int = super.getFuelTime(fuel) * 2


}
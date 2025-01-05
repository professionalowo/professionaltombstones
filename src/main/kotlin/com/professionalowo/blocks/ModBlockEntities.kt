package com.professionalowo.blocks

import com.professionalowo.Initializer
import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.blocks.tombstone.TombstoneBlockEntity
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.BlockEntityType.BlockEntityFactory
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModBlockEntities : Initializer() {
    val TOMBSTONE_BLOCK_ENTITY: BlockEntityType<TombstoneBlockEntity> =
        register("tombstone", ModBlocks.TOMBSTONE_BLOCK) { pos, state -> TombstoneBlockEntity(pos, state) }

    override fun initialize() {
        logger.info("Initialized BlockEntities")
    }

    private fun <T : BlockEntity> register(
        name: String,
        vararg blocks: Block,
        entityFactory: BlockEntityFactory<out T>
    ): BlockEntityType<T> {
        return Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(MOD_ID, name),
            BlockEntityType.Builder.create(entityFactory, *blocks).build()
        )
    }
}
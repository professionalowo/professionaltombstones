package com.professionalowo

import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.blocks.tombstone.TombstoneBlockEntity
import com.professionalowo.gamerules.allGamerules
import com.professionalowo.util.transferTo
import net.minecraft.inventory.Inventory
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.Properties
import net.minecraft.world.GameRules
import org.slf4j.LoggerFactory


fun afterDeath(player: ServerPlayerEntity) = player.run {
    val logger = LoggerFactory.getLogger(MOD_ID)

    val shouldSpawnGravestone = world.gameRules.allGamerules(
        ModGameRules.SPAWN_GRAVESTONE to true,
        GameRules.KEEP_INVENTORY to false
    )

    if (!shouldSpawnGravestone) {
        return
    }

    if (inventory.isEmpty) return

    world.setBlockState(
        blockPos,
        getBlock().defaultState
            .withIfExists(Properties.FACING, facing)
            .withIfExists(Properties.HORIZONTAL_FACING, facing)
    )

    val blockEntity = world.getBlockEntity(blockPos)

    val blockInventory = blockEntity as? Inventory ?: return

    val tombstoneBlockEntity = blockEntity as? TombstoneBlockEntity ?: return

    inventory.transferTo(blockInventory)

    tombstoneBlockEntity.setPlayer(this)

    logger.info("Saved inventory of ${name.literalString} at $blockPos")
}

private fun getBlock() = ModBlocks.TOMBSTONE_BLOCK




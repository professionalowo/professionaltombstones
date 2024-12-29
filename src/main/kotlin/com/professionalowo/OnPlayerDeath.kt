package com.professionalowo

import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.util.allGamerules
import com.professionalowo.util.getBlockInventory
import com.professionalowo.util.transferTo
import net.minecraft.block.Blocks
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.Properties
import net.minecraft.world.GameRules
import org.slf4j.LoggerFactory


fun afterDeath(player: ServerPlayerEntity) = player.run {
    val logger = LoggerFactory.getLogger(MOD_ID)

    val shouldSpawnGravestone = world.allGamerules(
        ModGameRules.SPAWN_GRAVESTONE to true,
        GameRules.KEEP_INVENTORY to false
    )

    if (!shouldSpawnGravestone) {
        return
    }

    world.setBlockState(
        blockPos,
        getBlock().defaultState
            .withIfExists(Properties.FACING, facing)
            .withIfExists(Properties.HORIZONTAL_FACING, facing)
    )

    val blockInventory = world.getBlockInventory(blockPos) ?: return

    inventory.transferTo(blockInventory)

    logger.info("Saved inventory of ${player.name.literalString} at $blockPos")
}

private fun getBlock() = Blocks.BARREL




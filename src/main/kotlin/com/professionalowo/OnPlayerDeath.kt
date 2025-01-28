package com.professionalowo

import com.professionalowo.gamerules.ModGameRules
import com.professionalowo.blocks.ModBlocks
import com.professionalowo.blocks.tombstone.TombstoneBlockEntity
import com.professionalowo.gamerules.allGamerules
import com.professionalowo.util.createLogger
import com.professionalowo.util.nextSolidBlockDown
import com.professionalowo.util.transferTo
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.GameRules


fun afterDeath(player: PlayerEntity) = player.run {

    if (inventory.isEmpty) return

    val shouldSpawnGravestone = world.gameRules.allGamerules(
        ModGameRules.SPAWN_GRAVESTONE to true,
        GameRules.KEEP_INVENTORY to false
    )

    if (!shouldSpawnGravestone) {
        return
    }

    val tombstoneBlockEntity = createTombstone() ?: return

    inventory.transferTo(tombstoneBlockEntity)

    tombstoneBlockEntity.setPlayer(this)

    createLogger().info("Saved inventory of ${name.literalString} at $blockPos")
}

private fun PlayerEntity.createTombstone() =
    world.getBlockEntity(placeTombstone()) as? TombstoneBlockEntity


private fun PlayerEntity.placeTombstone(): BlockPos =
    getNextSolidBlockDown().also { world.setBlockState(it, getTombstoneBlockState()) }

private fun PlayerEntity.getNextSolidBlockDown() = blockPos.nextSolidBlockDown(world)

private fun PlayerEntity.getTombstoneBlockState() =
    ModBlocks.TOMBSTONE_BLOCK.defaultState.withIfExists(Properties.FACING, facing)



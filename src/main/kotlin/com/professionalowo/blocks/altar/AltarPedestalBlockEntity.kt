package com.professionalowo.blocks.altar

import com.professionalowo.blocks.ModBlockEntities
import com.professionalowo.util.addParticle
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.particle.ItemStackParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

class AltarPedestalBlockEntity(pos: BlockPos, state: BlockState) :
    AbstractAltarBlockEntity(ModBlockEntities.ALTAR_PEDESTAL_BLOCK_ENTITY, pos, state) {
    fun consumeItem(world: World) {
        if (!item.isEmpty && world.isClient) {
            val random = world.random
            for (i in 0..10) {
                world.addParticle(
                    ItemStackParticleEffect(ParticleTypes.ITEM, item.copy()),
                    Vec3d.ofCenter(pos.up()),
                    Vec3d(
                        random.nextGaussian() * 0.05,
                        random.nextDouble() * 0.1,
                        random.nextGaussian() * 0.05
                    )
                )
            }
        }
        clear()
        if (!world.isClient) {
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(cachedState))
            world.updateListeners(pos, cachedState, cachedState, Block.NOTIFY_ALL)
        }
        markDirty()
    }
}
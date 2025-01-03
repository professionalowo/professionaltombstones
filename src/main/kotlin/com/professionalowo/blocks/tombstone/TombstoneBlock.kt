package com.professionalowo.blocks.tombstone

import com.mojang.serialization.MapCodec
import com.professionalowo.blocks.voxels.HorizontalVoxelShape
import com.professionalowo.sound.ModSoundEvents
import com.professionalowo.util.or
import net.minecraft.block.*
import net.minecraft.block.BlockWithEntity.createCuboidShape
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.particle.ParticleTypes
import net.minecraft.sound.SoundCategory
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess


private fun createVoxelShape(): HorizontalVoxelShape {
    val north: VoxelShape = arrayOf(
        createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
        createCuboidShape(0.0, 2.0, 14.0, 16.0, 14.0, 16.0),
        createCuboidShape(2.0, 14.0, 14.0, 14.0, 16.0, 16.0),
        createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
    ).reduce { v1, v2 -> v1.or(v2) }


    val west: VoxelShape = arrayOf(
        createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
        createCuboidShape(14.0, 2.0, 0.0, 16.0, 14.0, 16.0),
        createCuboidShape(14.0, 14.0, 2.0, 16.0, 16.0, 14.0),
        createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
    ).reduce { v1, v2 -> v1.or(v2) }

    val south: VoxelShape = arrayOf(
        createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
        createCuboidShape(0.0, 2.0, 0.0, 16.0, 14.0, 2.0),
        createCuboidShape(2.0, 14.0, 0.0, 14.0, 16.0, 2.0),
        createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
    ).reduce { v1, v2 -> v1.or(v2) }

    val east: VoxelShape = arrayOf(
        createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
        createCuboidShape(0.0, 2.0, 0.0, 2.0, 14.0, 16.0),
        createCuboidShape(0.0, 14.0, 2.0, 2.0, 16.0, 14.0),
        createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
    ).reduce { v1, v2 -> v1.or(v2) }

    return HorizontalVoxelShape(north, east, south, west)
}

class TombstoneBlock(settings: Settings) : BlockWithEntity(settings), Waterloggable {
    companion object {
        val FACING: DirectionProperty = Properties.HORIZONTAL_FACING
        val WATERLOGGED: BooleanProperty = Properties.WATERLOGGED

        val voxelShape: HorizontalVoxelShape = createVoxelShape()
    }

    init {
        defaultState = stateManager.defaultState
            .with(WATERLOGGED, false)
            .with(FACING, Direction.NORTH)
    }

    override fun getCodec(): MapCodec<out TombstoneBlock> = createCodec { TombstoneBlock(it) }

    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = TombstoneBlockEntity(pos, state)

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING, WATERLOGGED)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        defaultState.with(FACING, ctx.horizontalPlayerFacing.opposite)
            .with(WATERLOGGED, ctx.world.getFluidState(ctx.blockPos).fluid == Fluids.WATER)

    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState =
        state.with(FACING, rotation.rotate(state.get(FACING)))


    override fun mirror(state: BlockState, mirror: BlockMirror): BlockState =
        state.rotate(mirror.getRotation(state.get(FACING)))

    override fun getFluidState(state: BlockState): FluidState =
        if (state.get(WATERLOGGED)) Fluids.WATER.getStill(false) else super.getFluidState(state)


    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos?
    ): BlockState {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world))
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun hasSidedTransparency(state: BlockState?): Boolean = true

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = voxelShape[state?.get(FACING)]


    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hit: BlockHitResult
    ): ActionResult {
        world.breakBlock(pos, false, player)
        return ActionResult.SUCCESS
    }

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        ItemScatterer.onStateReplaced(state, newState, world, pos)
        super.onStateReplaced(state, world, pos, newState, moved)
    }

    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        val blockEntity = world.getBlockEntity(pos) as? TombstoneBlockEntity ?: return
        //only summon particles if there are items
        if (blockEntity.isEmpty) return

        //summon particles not so often
        if (random.nextInt(4) == 0) {
            for (i in 0 until random.nextInt(2)) {
                world.addParticle(
                    ParticleTypes.SOUL,
                    pos.x.toDouble() + random.nextFloat(),
                    pos.y.toDouble() + 0.5,
                    pos.z.toDouble() + random.nextFloat(),
                    0.0,
                    (random.nextFloat() / 20.0f).toDouble(),
                    0.0
                )
            }
        }

        if (random.nextInt(15) == 0) {
            world.playSound(
                pos.x.toDouble() + 0.5,
                pos.y.toDouble() + 0.5,
                pos.z.toDouble() + 0.5,
                ModSoundEvents.TOMBSTONE_CREAKING,
                SoundCategory.BLOCKS,
                0.2f + random.nextFloat(),
                random.nextFloat() * 0.7f + 0.6f,
                true
            )
        }
    }
}
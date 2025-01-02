package com.professionalowo.blocks.tombstone

import com.mojang.serialization.MapCodec
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.ItemScatterer
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.stream.Stream


class TombstoneBlock(settings: Settings) : BlockWithEntity(settings) {
    companion object {
        val SHAPE_N: VoxelShape = Stream.of(
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            createCuboidShape(0.0, 2.0, 14.0, 16.0, 14.0, 16.0),
            createCuboidShape(2.0, 14.0, 14.0, 14.0, 16.0, 16.0),
            createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
        ).reduce { v1, v2 -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }.get()

        val SHAPE_W: VoxelShape = Stream.of(
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            createCuboidShape(14.0, 2.0, 0.0, 16.0, 14.0, 16.0),
            createCuboidShape(14.0, 14.0, 2.0, 16.0, 16.0, 14.0),
            createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
        ).reduce { v1: VoxelShape?, v2: VoxelShape? -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }
            .get()
        val SHAPE_S: VoxelShape = Stream.of(
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            createCuboidShape(0.0, 2.0, 0.0, 16.0, 14.0, 2.0),
            createCuboidShape(2.0, 14.0, 0.0, 14.0, 16.0, 2.0),
            createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
        ).reduce { v1: VoxelShape?, v2: VoxelShape? -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }
            .get()
        val SHAPE_E: VoxelShape = Stream.of(
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            createCuboidShape(0.0, 2.0, 0.0, 2.0, 14.0, 16.0),
            createCuboidShape(0.0, 14.0, 2.0, 2.0, 16.0, 14.0),
            createCuboidShape(2.0, 2.0, 2.0, 14.0, 4.0, 14.0)
        ).reduce { v1: VoxelShape?, v2: VoxelShape? -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }
            .get()
        val FACING: DirectionProperty = Properties.HORIZONTAL_FACING
    }

    override fun getCodec(): MapCodec<out BlockWithEntity> = createCodec { TombstoneBlock(it) }

    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = TombstoneBlockEntity(pos, state)

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        defaultState.with(FACING, ctx.horizontalPlayerFacing.opposite)

    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState =
        state.with(FACING, rotation.rotate(state.get(FACING)))


    override fun mirror(state: BlockState, mirror: BlockMirror): BlockState =
        state.rotate(mirror.getRotation(state.get(FACING)))

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape = when (state?.get(FACING)) {
        Direction.NORTH -> SHAPE_N
        Direction.SOUTH -> SHAPE_S
        Direction.WEST -> SHAPE_W
        Direction.EAST -> SHAPE_E
        else -> SHAPE_N
    }

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
}
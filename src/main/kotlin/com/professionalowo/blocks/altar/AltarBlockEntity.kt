package com.professionalowo.blocks.altar

import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import kotlin.random.Random

class AltarBlockEntity(pos: BlockPos, state: BlockState?) :
    BlockEntity(ModBlockEntities.ALTAR_BLOCK_ENTITY, pos, state),
    SidedInventory {
    private val itemSlot: DefaultedList<ItemStack> = DefaultedList.ofSize(1, ItemStack.EMPTY)

    var ticks: Int = Random.nextInt(361)
        set(value) {
            field = value % 361
        }

    override fun size(): Int = itemSlot.size
    override fun isEmpty(): Boolean = itemSlot.isEmpty()

    override fun clear() = itemSlot.clear()

    override fun getStack(slot: Int): ItemStack = if (slot == 0) itemSlot[slot] else ItemStack.EMPTY
    override fun removeStack(slot: Int): ItemStack =
        Inventories.removeStack(itemSlot, slot)

    override fun removeStack(slot: Int, amount: Int): ItemStack =
        Inventories.splitStack(itemSlot, slot, amount)
            .also {
                if (it.isEmpty) {
                    this.markDirty()
                }
            }

    override fun setStack(slot: Int, stack: ItemStack) {
        itemSlot[slot] = stack
        stack.capCount(getMaxCount(stack))
        markDirty()
    }

    override fun isValid(slot: Int, stack: ItemStack?): Boolean = slot in itemSlot.indices && itemSlot[slot].isEmpty

    override fun canTransferTo(hopperInventory: Inventory?, slot: Int, stack: ItemStack): Boolean =
        slot in itemSlot.indices && !itemSlot[slot].isEmpty

    override fun getAvailableSlots(side: Direction?): IntArray = IntArray(itemSlot.size)

    override fun canInsert(slot: Int, stack: ItemStack?, dir: Direction?): Boolean = dir == Direction.UP

    override fun canExtract(slot: Int, stack: ItemStack?, dir: Direction?): Boolean = false

    override fun getMaxCountPerStack(): Int = 1

    override fun canPlayerUse(player: PlayerEntity?): Boolean = Inventory.canPlayerUse(this, player)

    override fun readNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.readNbt(nbt, registryLookup)
        Inventories.readNbt(nbt, itemSlot, registryLookup)
        if (nbt.contains("Ticks", NbtElement.INT_TYPE.toInt())) {
            ticks = nbt.getInt("Ticks")
        }
    }

    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.writeNbt(nbt, registryLookup)
        Inventories.writeNbt(nbt, itemSlot, registryLookup)
        nbt.putInt("Ticks", ticks)
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener> = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt(registryLookup: RegistryWrapper.WrapperLookup): NbtCompound =
        createNbt(registryLookup)

    override fun markDirty() {
        world?.updateListeners(pos, cachedState, cachedState, Block.NOTIFY_ALL)
        super.markDirty()
    }
}
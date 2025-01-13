package com.professionalowo.blocks.altar

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

abstract class AbstractAltarBlockEntity(type: BlockEntityType<*>, pos: BlockPos, state: BlockState?) :
    BlockEntity(type, pos, state),
    Inventory {
    private val itemSlot = DefaultedList.ofSize(1, ItemStack.EMPTY)

    override fun size(): Int = itemSlot.size
    override fun isEmpty(): Boolean = itemSlot.isEmpty()
    override fun clear() = itemSlot.clear()

    override fun getStack(slot: Int): ItemStack = if (slot > size()) ItemStack.EMPTY else itemSlot[slot]

    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(itemSlot, slot)
    override fun removeStack(slot: Int, amount: Int): ItemStack = Inventories.splitStack(itemSlot, slot, amount).also {
        if (!it.isEmpty) {
            markDirty()
        }
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        itemSlot[slot] = stack.apply { capCount(getMaxCount(this)) }.also { markDirty() }
    }

    override fun canPlayerUse(player: PlayerEntity?): Boolean = Inventory.canPlayerUse(this, player)

    override fun readNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.readNbt(nbt, registryLookup)
        Inventories.readNbt(nbt, itemSlot, registryLookup)
    }

    override fun writeNbt(nbt: NbtCompound?, registryLookup: RegistryWrapper.WrapperLookup?) {
        Inventories.writeNbt(nbt, itemSlot, registryLookup)
        super.writeNbt(nbt, registryLookup)
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt(registryLookup: RegistryWrapper.WrapperLookup): NbtCompound =
        createNbt(registryLookup)

    override fun getMaxCountPerStack(): Int = 1
}
package com.professionalowo.blocks.altar

import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.Packet
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.registry.RegistryWrapper
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import kotlin.math.min
import kotlin.random.Random

class AltarBlockEntity(pos: BlockPos, state: BlockState?) :
    BlockEntity(ModBlockEntities.ALTAR_BLOCK_ENTITY, pos, state),
    Inventory {
    private val itemSlot = DefaultedList.ofSize(1, ItemStack.EMPTY)

    var ticks: Int = Random.nextInt(360)
        set(value) {
            field = min(value, 360)
        }

    var item: ItemStack
        get() = getStack(0)
        set(value) = setStack(0, value)

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

    override fun readNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup?) {
        super.readNbt(nbt, registryLookup)
        if (nbt.contains("Ticks", NbtElement.INT_TYPE.toInt())) {
            ticks = nbt.getInt("Ticks")
        }
        Inventories.readNbt(nbt, itemSlot, registryLookup)
    }

    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup?) {
        Inventories.writeNbt(nbt, itemSlot, registryLookup)
        nbt.putInt("Ticks", ticks)
        super.writeNbt(nbt, registryLookup)
    }

    override fun toUpdatePacket(): Packet<ClientPlayPacketListener>? = BlockEntityUpdateS2CPacket.create(this)

    override fun toInitialChunkDataNbt(registryLookup: RegistryWrapper.WrapperLookup): NbtCompound =
        createNbt(registryLookup)

    override fun getMaxCountPerStack(): Int = 1
}
package com.professionalowo.blocks.tombstone

import com.professionalowo.blocks.ModBlockEntities
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.registry.RegistryWrapper
import net.minecraft.text.Text
import net.minecraft.util.Nameable
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos

class TombstoneBlockEntity(pos: BlockPos, state: BlockState?) :
    BlockEntity(ModBlockEntities.TOMBSTONE_BLOCK_ENTITY, pos, state), Inventory, Nameable {
    companion object {
        private const val INVENTORY_SIZE = 27 + 9 + 4 + 1
    }

    private var customName: Text? = null
    private var playerUuid: String? = null

    private val inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY)

    override fun size(): Int = INVENTORY_SIZE

    override fun clear() = inventory.clear()

    override fun isEmpty(): Boolean = inventory.isEmpty()

    override fun getStack(slot: Int): ItemStack = if (slot > INVENTORY_SIZE) ItemStack.EMPTY else inventory[slot]

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val itemStack = Inventories.splitStack(inventory, slot, amount)
        if (!itemStack.isEmpty) {
            markDirty()
        }

        return itemStack
    }

    override fun removeStack(slot: Int): ItemStack = Inventories.removeStack(inventory, slot)

    override fun setStack(slot: Int, stack: ItemStack?) {
        inventory[slot] = stack
        stack?.capCount(getMaxCount(stack))
        markDirty()
    }

    override fun canPlayerUse(player: PlayerEntity?): Boolean = Inventory.canPlayerUse(this, player)

    override fun getName(): Text = customName ?: Text.translatable("block.professionaltombstone.tombstone")

    override fun readNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.readNbt(nbt, registryLookup)
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE.toInt())) {
            this.customName = tryParseCustomName(nbt.getString("CustomName"), registryLookup)
        }
        if (nbt.contains("PlayerUuid", NbtElement.STRING_TYPE.toInt())) {
            this.playerUuid = nbt.getString("PlayerUuid")
        }
        Inventories.readNbt(nbt, inventory, registryLookup)
    }

    override fun writeNbt(nbt: NbtCompound, registryLookup: RegistryWrapper.WrapperLookup) {
        super.writeNbt(nbt, registryLookup)
        if (customName != null) {
            nbt.putString("CustomName", Text.Serialization.toJsonString(this.customName, registryLookup))
        }
        if (playerUuid != null) {
            nbt.putString("PlayerUuid", playerUuid)
        }
        Inventories.writeNbt(nbt, inventory, registryLookup)
    }

    fun setPlayer(player: PlayerEntity) {
        this.playerUuid = player.uuidAsString
        this.customName = player.name
    }
}
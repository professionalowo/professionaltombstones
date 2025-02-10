package com.professionalowo.player_data

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound

class PlayerDataAccessor<P : PlayerEntity>(val player: P) : IPlayerDataHandler {
    companion object {
        const val ESSENCE_VALUE_KEY = "essence_value_key"
        const val ESSENCE_MAX_KEY = "essence_max_key"
    }

    val handler: IPlayerDataHandler =
        player as? IPlayerDataHandler ?: throw IllegalStateException("Mixins might not have been initialized")

    override fun getNbtData(): NbtCompound = handler.getNbtData()

    var essence: Int
        get() = getNbtData().getInt(ESSENCE_VALUE_KEY)
        set(value) = getNbtData().putInt(ESSENCE_VALUE_KEY, value)

    var maxEssence: Int
        get() = getNbtData().getInt(ESSENCE_MAX_KEY)
        set(value) = getNbtData().putInt(ESSENCE_MAX_KEY, value)

    init {
        val nbt = getNbtData()
        if (!nbt.contains(ESSENCE_VALUE_KEY)) {
            nbt.putInt(ESSENCE_VALUE_KEY, 0)
        }
        if (!nbt.contains(ESSENCE_MAX_KEY)) {
            nbt.putInt(ESSENCE_MAX_KEY, 10)
        }
    }

    fun hasEnoughEssence(value: Int): Boolean = essence >= value

    fun addEssence(value: Int) {
        essence = minOf(essence + value, maxEssence)
    }

    fun regenerateEssence() = addEssence(getEssenceRegenPerSecond())

    fun decrementEssence(value: Int) {
        essence = maxOf(essence - value, 0)
    }

    private fun getEssenceRegenPerSecond(): Int = 1
}
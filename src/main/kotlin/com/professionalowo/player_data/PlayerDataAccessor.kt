package com.professionalowo.player_data

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound

class PlayerDataAccessor(val player: PlayerEntity) : IPlayerDataHandler {
    companion object {
        const val MANA_VALUE_KEY = "mana_value_key"
        const val MAX_MANA_KEY = "max_mana_key"
    }

    val handler: IPlayerDataHandler =
        player as? IPlayerDataHandler ?: throw IllegalStateException("Mixins might not have been initialized")

    override fun getNbtData(): NbtCompound = handler.getNbtData()

    var mana: Int
        get() = getNbtData().getInt(MANA_VALUE_KEY)
        set(value) = getNbtData().putInt(MANA_VALUE_KEY, value)

    var maxMana: Int
        get() = getNbtData().getInt(MAX_MANA_KEY)
        set(value) = getNbtData().putInt(MAX_MANA_KEY, value)

    init {
        val nbt = getNbtData()
        if (!nbt.contains(MANA_VALUE_KEY)) {
            nbt.putInt(MANA_VALUE_KEY, 0)
        }
        if (!nbt.contains(MAX_MANA_KEY)) {
            nbt.putInt(MAX_MANA_KEY, 10)
        }
    }

    fun decrementMana(value: Int) {
        mana = maxOf(mana - value, 0)
    }

    fun getManaRegenPerSecond(): Int = 1
}
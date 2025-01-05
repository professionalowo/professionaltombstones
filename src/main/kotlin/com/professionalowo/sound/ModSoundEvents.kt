package com.professionalowo.sound

import com.professionalowo.Initializer
import com.professionalowo.util.modIdentifier
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents

object ModSoundEvents : Initializer() {
    val TOMBSTONE_CREAKING: SoundEvent = SoundEvents.PARTICLE_SOUL_ESCAPE.value()

    override fun initialize() {
        logger.info("Initialized Sound Events")
    }

    private fun registerSound(id: String): SoundEvent {
        val identifier = modIdentifier(id)
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier))
    }
}
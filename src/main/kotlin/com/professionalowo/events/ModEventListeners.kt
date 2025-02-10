package com.professionalowo.events

import com.professionalowo.Initializer
import com.professionalowo.events.essence.ServerTickEventListenerEssenceRegeneration
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents

object ModEventListeners : Initializer() {
    override fun initialize() {
        ServerTickEvents.END_SERVER_TICK.register(ServerTickEventListenerEssenceRegeneration())
    }
}
package com.professionalowo.screen

import com.mojang.serialization.Codec
import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.gamerules.ModGameRules
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.option.GameOptionsScreen
import net.minecraft.client.gui.widget.Widget
import net.minecraft.client.option.GameOptions
import net.minecraft.client.option.SimpleOption
import net.minecraft.server.integrated.IntegratedServer
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class ModConfigScreen(parent: Screen?) : GameOptionsScreen(
    parent, MinecraftClient.getInstance().options,
    Text.literal("$MOD_ID options")
) {

    override fun addOptions() {
        if (client?.world != null && client?.isIntegratedServerRunning == true) {
            body?.addAll(getSpawnGravestoneOption())
        }
    }

    private fun getSpawnGravestoneOption() = SimpleOption(
        "options.$MOD_ID.spawn_gravestone",
        SimpleOption.emptyTooltip(),
        { _: Text, value: Boolean -> Text.literal(value.toString()) },
        SimpleOption.BOOLEAN,
        getSpawnGravestoneValue()
    ) {
        if (client?.world != null && client?.isIntegratedServerRunning == true) {
            val server = client?.server ?: return@SimpleOption
            client?.server?.gameRules?.get(ModGameRules.SPAWN_GRAVESTONE)?.set(it, server)
        }
    }

    private fun getSpawnGravestoneValue() = client?.server?.gameRules?.get(ModGameRules.SPAWN_GRAVESTONE)?.get() ?: true
}
package com.professionalowo.options

import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.gamerules.ModGameRules
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.SimpleOption
import net.minecraft.text.Text

object ModOptionsManager {
    fun getOptions(client: MinecraftClient?): Array<SimpleOption<*>> =
        arrayListOf(*getIntegratedWorldOptions(client)).filterNotNull().toTypedArray()


    private fun getIntegratedWorldOptions(client: MinecraftClient?): Array<SimpleOption<*>?> = arrayOf(
        getSpawnGravestoneOption(client),
    )

    private fun createOptionKey(name: String): String = "options.${MOD_ID}.$name"


    private fun getSpawnGravestoneOption(client: MinecraftClient?) =
        if (client?.world != null && client.isIntegratedServerRunning) SimpleOption(
            createOptionKey("spawn_gravestone"),
            SimpleOption.emptyTooltip(),
            { _, value -> Text.literal(value.toString()) },
            SimpleOption.BOOLEAN,
            client.server?.gameRules?.get(ModGameRules.SPAWN_GRAVESTONE)?.get() ?: true
        ) {
            val server = client.server ?: return@SimpleOption
            server.gameRules?.get(ModGameRules.SPAWN_GRAVESTONE)?.set(it, server)
        } else null

}


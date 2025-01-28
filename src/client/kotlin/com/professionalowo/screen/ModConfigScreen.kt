package com.professionalowo.screen

import com.professionalowo.Professionaltombstones.MOD_ID
import com.professionalowo.options.ModOptionsManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.option.GameOptionsScreen
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class ModConfigScreen(parent: Screen?) : GameOptionsScreen(
    parent, MinecraftClient.getInstance().options,
    Text.literal("$MOD_ID options")
) {

    override fun addOptions() {
        body?.addAll(*ModOptionsManager.getOptions(client))
    }
}
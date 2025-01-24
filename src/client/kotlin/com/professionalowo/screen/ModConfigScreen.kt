package com.professionalowo.screen

import com.professionalowo.Professionaltombstones.MOD_ID
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class ModConfigScreen(private val parent: Screen?) : Screen(Text.literal(MOD_ID)) {
    override fun close() = client?.setScreen(parent) ?: Unit

    override fun init() {
        val closeButton = ButtonWidget.builder(Text.literal("Done")) {
            close()
        }.build()
        closeButton.setPosition((width - closeButton.width) / 2, (height - closeButton.height) / 2)
        addDrawableChild(closeButton)
    }
}
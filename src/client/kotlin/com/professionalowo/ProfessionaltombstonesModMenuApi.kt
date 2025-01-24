package com.professionalowo

import com.professionalowo.screen.ModConfigScreen
import com.terraformersmc.modmenu.api.*

object ProfessionaltombstonesModMenuApi : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> = ConfigScreenFactory {
        ModConfigScreen(it)
    }

    override fun getUpdateChecker(): UpdateChecker? = null

}
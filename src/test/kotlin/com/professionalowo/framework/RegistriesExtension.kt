package com.professionalowo.framework

import net.minecraft.Bootstrap
import net.minecraft.SharedConstants
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class RegistriesExtension:BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
        SharedConstants.createGameVersion()
        Bootstrap.initialize()
    }
}
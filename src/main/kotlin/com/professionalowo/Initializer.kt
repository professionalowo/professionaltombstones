package com.professionalowo

import com.professionalowo.util.createLogger

abstract class Initializer {
    val logger = createLogger()

    abstract fun initialize()
}
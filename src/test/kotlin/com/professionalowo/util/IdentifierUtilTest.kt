package com.professionalowo.util

import com.professionalowo.Professionaltombstones.MOD_ID
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class IdentifierUtilTest {
    @Test
    fun modIdentifier_shouldStartWithModId(){
        val name = "test_id"
        val result = modIdentifier(name).toString()
        val expected = "$MOD_ID:$name"
        assertEquals(expected, result)
    }
}
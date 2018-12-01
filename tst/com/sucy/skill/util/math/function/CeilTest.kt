package com.sucy.skill.util.math.function

import com.sucy.skill.Assert.assertEquals
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class CeilTest : TokenTest() {
    @Test
    fun getToken() {
        assertEquals("ceil", Ceil.token)
    }

    @Test
    fun apply() {
        assertEquals(1.0, test(Ceil, 1.0))
        assertEquals(1.0, test(Ceil, 0.9))
        assertEquals(1.0, test(Ceil, 0.2))
        assertEquals(123.0, test(Ceil, 122.01))
    }
}
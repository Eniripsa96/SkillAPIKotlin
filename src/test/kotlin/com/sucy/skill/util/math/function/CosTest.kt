package com.sucy.skill.util.math.function

import com.sucy.skill.Assert.assertEquals
import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Cos
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class CosTest : TokenTest() {
    @Test
    fun getToken() {
        assertEquals("cos", Cos.token)
    }

    @Test
    fun apply() {
        assertEquals(1.0, test(Cos, 0.0))
        assertEquals(0.0, test(Cos, 90.0))
        assertEquals(-1.0, test(Cos, 180.0))
        assertEquals(0.0, test(Cos, 270.0))
    }
}
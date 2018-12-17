package com.sucy.skill.util.math.function

import com.sucy.skill.Assert.assertEquals
import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Abs
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class AbsTest : TokenTest() {

    @Test
    fun getToken() {
        assertEquals("abs", Abs.token)
    }

    @Test
    fun apply() {
        assertEquals(1.0, test(Abs, 1.0))
        assertEquals(1.0, test(Abs, -1.0))
        assertEquals(123.45, test(Abs, -123.45))
    }
}
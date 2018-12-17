package com.sucy.skill.util.math.function

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Tan
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class TanTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals("tan", Tan.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(0.0, test(Tan, 0.0))
        Assert.assertEquals(1.0, test(Tan, 45.0))
        Assert.assertEquals(Math.sqrt(3.0), test(Tan, 60.0))
        Assert.assertEquals(-1.0, test(Tan, 135.0))
    }
}
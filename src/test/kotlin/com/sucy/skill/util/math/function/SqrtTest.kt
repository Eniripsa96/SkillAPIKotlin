package com.sucy.skill.util.math.function

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Sqrt
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SqrtrtTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals("sqrt", Sqrt.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(0.0, test(Sqrt, 0.0))
        Assert.assertEquals(1.0, test(Sqrt, 1.0))
        Assert.assertEquals(4.0, test(Sqrt, 16.0))
        Assert.assertEquals(2.4, test(Sqrt, 5.76))
    }
}
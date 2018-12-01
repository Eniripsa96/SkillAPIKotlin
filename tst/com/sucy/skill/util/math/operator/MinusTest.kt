package com.sucy.skill.util.math.operator

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class MinusTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals('-', Minus.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(-2.0, test(Minus, 2.0, 4.0))
        Assert.assertEquals(2.0, test(Minus, 4.0, 2.0))
        Assert.assertEquals(3.0, test(Minus, 7.0, 4.0))
        Assert.assertEquals(-123.0, test(Minus, 123.0, 246.0))
    }
}
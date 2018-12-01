package com.sucy.skill.util.math.operator

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class PowTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals('^', Pow.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(16.0, test(Pow, 2.0, 4.0))
        Assert.assertEquals(16.0, test(Pow, 4.0, 2.0))
        Assert.assertEquals(2401.0, test(Pow, 7.0, 4.0))
        Assert.assertEquals(64.0, test(Pow, 16.0, 1.5))
    }
}
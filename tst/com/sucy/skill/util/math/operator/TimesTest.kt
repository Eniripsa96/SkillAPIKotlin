package com.sucy.skill.util.math.operator

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class TimesTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals('*', Times.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(8.0, test(Times, 2.0, 4.0))
        Assert.assertEquals(8.0, test(Times, 4.0, 2.0))
        Assert.assertEquals(28.0, test(Times, 7.0, 4.0))
        Assert.assertEquals(30258.0, test(Times, 123.0, 246.0))
    }
}
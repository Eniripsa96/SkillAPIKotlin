package com.sucy.skill.util.math.function

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SinTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals("sin", Sin.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(0.0, test(Sin, 0.0))
        Assert.assertEquals(1.0, test(Sin, 90.0))
        Assert.assertEquals(0.0, test(Sin, 180.0))
        Assert.assertEquals(-1.0, test(Sin, 270.0))
    }
}
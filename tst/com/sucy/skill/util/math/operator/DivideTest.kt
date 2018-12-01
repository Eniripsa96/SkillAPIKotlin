package com.sucy.skill.util.math.operator

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class DivideTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals('/', Divide.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(0.5, test(Divide, 2.0, 4.0))
        Assert.assertEquals(2.0, test(Divide, 4.0, 2.0))
        Assert.assertEquals(1.75, test(Divide, 7.0, 4.0))
        Assert.assertEquals(0.5, test(Divide, 123.0, 246.0))
    }
}
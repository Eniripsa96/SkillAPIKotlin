package com.sucy.skill.util.math.function

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SignTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals("sign", Sign.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(1.0, test(Sign, 1.0))
        Assert.assertEquals(1.0, test(Sign, 2.3))
        Assert.assertEquals(-1.0, test(Sign, -4.3))
        Assert.assertEquals(1.0, test(Sign, 1492.3271923))
        Assert.assertEquals(0.0, test(Sign, 0.0))
    }
}
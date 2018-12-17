package com.sucy.skill.util.math.function

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Log
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class LogTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals("log", Log.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(0.0, test(Log, 0.0))
        Assert.assertEquals(0.0, test(Log, 1.0))
        Assert.assertEquals(1.0, test(Log, Math.E))
        Assert.assertEquals(2.0, test(Log, Math.E * Math.E))
    }
}
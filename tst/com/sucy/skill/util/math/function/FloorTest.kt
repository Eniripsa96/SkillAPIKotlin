package com.sucy.skill.util.math.function

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Floor
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class FloorTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals("floor", Floor.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(1.0, test(Floor, 1.0))
        Assert.assertEquals(0.0, test(Floor, 0.9))
        Assert.assertEquals(0.0, test(Floor, 0.2))
        Assert.assertEquals(122.0, test(Floor, 122.01))
    }
}
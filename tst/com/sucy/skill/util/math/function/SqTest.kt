package com.sucy.skill.util.math.function

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SqTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals("sq", Sq.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(0.0, test(Sq, 0.0))
        Assert.assertEquals(1.0, test(Sq, 1.0))
        Assert.assertEquals(16.0, test(Sq, 4.0))
        Assert.assertEquals(5.76, test(Sq, 2.4))
    }
}
package com.sucy.skill.util.math.operator

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Plus
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class PlusTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals('+', Plus.token)
    }

    @Test
    fun apply() {
        Assert.assertEquals(5.0, test(Plus, 2.0, 3.0))
        Assert.assertEquals(4.6, test(Plus, 1.2, 3.4))
        Assert.assertEquals(2.2, test(Plus, 3.4, -1.2))
        Assert.assertEquals(691.2, test(Plus, 123.4, 567.8))
    }
}
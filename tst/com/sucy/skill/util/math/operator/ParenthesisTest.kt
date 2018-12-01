package com.sucy.skill.util.math.operator

import com.sucy.skill.Assert
import com.sucy.skill.util.math.TokenTest
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class ParenthesisTest : TokenTest() {
    @Test
    fun getToken() {
        Assert.assertEquals('(', Parenthesis.token)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun apply() {
        test(Parenthesis, 1.0)
    }
}
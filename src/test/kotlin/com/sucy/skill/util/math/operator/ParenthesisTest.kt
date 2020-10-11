package com.sucy.skill.util.math.operator

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Parenthesis
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class ParenthesisTest : TokenTest() {
    @Test
    fun getToken() {
        Parenthesis.token shouldBe '('
    }

    @Test(expected = UnsupportedOperationException::class)
    fun apply() {
        test(Parenthesis, 1.0)
    }
}
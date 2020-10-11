package com.sucy.skill.util.math.operator

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Plus
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class PlusTest : TokenTest() {
    @Test
    fun getToken() {
        Plus.token shouldBe '+'
    }

    @Test
    fun apply() {
        test(Plus, 2.0, 3.0) shouldBe(5.0).plusOrMinus(EPSILON)
        test(Plus, 1.2, 3.4) shouldBe(4.6).plusOrMinus(EPSILON)
        test(Plus, 3.4, -1.2) shouldBe(2.2).plusOrMinus(EPSILON)
        test(Plus, 123.4, 567.8) shouldBe(691.2).plusOrMinus(EPSILON)
    }
}
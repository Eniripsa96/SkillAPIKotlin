package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Tan
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import org.junit.Test
import kotlin.math.sqrt

/**
 * SkillAPIKotlin © 2018
 */
class TanTest : TokenTest() {
    @Test
    fun getToken() {
        Tan.token shouldBe "tan"
    }

    @Test
    fun apply() {
        test(Tan, 0.0) shouldBe(0.0).plusOrMinus(EPSILON)
        test(Tan, 45.0) shouldBe(1.0).plusOrMinus(EPSILON)
        test(Tan, 60.0) shouldBe(sqrt(3.0)).plusOrMinus(EPSILON)
        test(Tan, 135.0) shouldBe(-1.0).plusOrMinus(EPSILON)
    }
}
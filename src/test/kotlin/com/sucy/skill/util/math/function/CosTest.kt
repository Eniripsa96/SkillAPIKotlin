package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Cos
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class CosTest : TokenTest() {
    @Test
    fun getToken() {
        Cos.token shouldBe "cos"
    }

    @Test
    fun apply() {
        test(Cos, 0.0) shouldBe(1.0).plusOrMinus(EPSILON)
        test(Cos, 90.0) shouldBe(0.0).plusOrMinus(EPSILON)
        test(Cos, 180.0) shouldBe(-1.0).plusOrMinus(EPSILON)
        test(Cos, 270.0) shouldBe(0.0).plusOrMinus(EPSILON)
    }
}
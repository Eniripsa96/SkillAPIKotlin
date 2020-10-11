package com.sucy.skill.util.math.operator

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Minus
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class MinusTest : TokenTest() {
    @Test
    fun getToken() {
        Minus.token shouldBe '-'
    }

    @Test
    fun apply() {
        test(Minus, 2.0, 4.0) shouldBe -2.0
        test(Minus, 4.0, 2.0) shouldBe 2.0
        test(Minus, 7.0, 4.0) shouldBe 3.0
        test(Minus, 123.0, 246.0) shouldBe -123.0
    }
}
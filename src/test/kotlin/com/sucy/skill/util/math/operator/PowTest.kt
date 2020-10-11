package com.sucy.skill.util.math.operator

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Pow
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class PowTest : TokenTest() {
    @Test
    fun getToken() {
        Pow.token shouldBe '^'
    }

    @Test
    fun apply() {
        test(Pow, 2.0, 4.0) shouldBe 16.0
        test(Pow, 4.0, 2.0) shouldBe 16.0
        test(Pow, 7.0, 4.0) shouldBe 2401.0
        test(Pow, 16.0, 1.5) shouldBe 64.0
    }
}
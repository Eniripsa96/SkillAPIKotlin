package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Sqrt
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SqrtrtTest : TokenTest() {
    @Test
    fun getToken() {
        Sqrt.token shouldBe "sqrt"
    }

    @Test
    fun apply() {
        test(Sqrt, 0.0) shouldBe 0.0
        test(Sqrt, 1.0) shouldBe 1.0
        test(Sqrt, 16.0) shouldBe 4.0
        test(Sqrt, 5.76) shouldBe 2.4
    }
}
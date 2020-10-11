package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Ceil
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class CeilTest : TokenTest() {
    @Test
    fun getToken() {
        Ceil.token shouldBe "ceil"
    }

    @Test
    fun apply() {
        test(Ceil, 1.0) shouldBe 1.0
        test(Ceil, 0.9) shouldBe 1.0
        test(Ceil, 0.2) shouldBe 1.0
        test(Ceil, 122.01) shouldBe 123.0
    }
}
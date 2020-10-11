package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Abs
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class AbsTest : TokenTest() {

    @Test
    fun getToken() {
        Abs.token shouldBe "abs"
    }

    @Test
    fun apply() {
        test(Abs, 1.0) shouldBe 1.0
        test(Abs, -1.0) shouldBe 1.0
        test(Abs, -123.45) shouldBe 123.45
    }
}
package com.sucy.skill.util.math.operator

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Times
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class TimesTest : TokenTest() {
    @Test
    fun getToken() {
        Times.token shouldBe '*'
    }

    @Test
    fun apply() {
        test(Times, 2.0, 4.0) shouldBe 8.0
        test(Times, 4.0, 2.0) shouldBe 8.0
        test(Times, 7.0, 4.0) shouldBe 28.0
        test(Times, 123.0, 246.0) shouldBe 30258.0
    }
}
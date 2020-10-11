package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Sin
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SinTest : TokenTest() {
    @Test
    fun getToken() {
        Sin.token shouldBe "sin"
    }

    @Test
    fun apply() {
        test(Sin, 0.0) shouldBe(0.0).plusOrMinus(EPSILON)
        test(Sin, 90.0) shouldBe(1.0).plusOrMinus(EPSILON)
        test(Sin, 180.0) shouldBe(0.0).plusOrMinus(EPSILON)
        test(Sin, 270.0) shouldBe(-1.0).plusOrMinus(EPSILON)
    }
}
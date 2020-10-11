package com.sucy.skill.util.math.operator

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Divide
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class DivideTest : TokenTest() {
    @Test
    fun getToken() {
        Divide.token shouldBe '/'
    }

    @Test
    fun apply() {
        test(Divide, 2.0, 4.0) shouldBe 0.5
        test(Divide, 4.0, 2.0) shouldBe 2.0
        test(Divide, 7.0, 4.0) shouldBe 1.75
        test(Divide, 123.0, 246.0) shouldBe 0.5
    }
}
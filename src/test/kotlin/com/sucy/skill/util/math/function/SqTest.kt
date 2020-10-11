package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Sq
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SqTest : TokenTest() {
    @Test
    fun getToken() {
        Sq.token shouldBe "sq"
    }

    @Test
    fun apply() {
        test(Sq, 0.0) shouldBe 0.0
        test(Sq, 1.0) shouldBe 1.0
        test(Sq, 4.0) shouldBe 16.0
        test(Sq, 2.4) shouldBe 5.76
    }
}
package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Sign
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class SignTest : TokenTest() {
    @Test
    fun getToken() {
        Sign.token shouldBe "sign"
    }

    @Test
    fun apply() {
        test(Sign, 1.0) shouldBe 1.0
        test(Sign, 2.3) shouldBe 1.0
        test(Sign, -4.3) shouldBe -1.0
        test(Sign, 1492.3271923) shouldBe 1.0
        test(Sign, 0.0) shouldBe 0.0
    }
}
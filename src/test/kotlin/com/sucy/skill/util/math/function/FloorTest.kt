package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Floor
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class FloorTest : TokenTest() {
    @Test
    fun getToken() {
        Floor.token shouldBe "floor"
    }

    @Test
    fun apply() {
        test(Floor, 1.0) shouldBe 1.0
        test(Floor, 0.9) shouldBe 0.0
        test(Floor, 0.2) shouldBe 0.0
        test(Floor, 122.01) shouldBe 122.0
    }
}
package com.sucy.skill.util.math.function

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.function.Log
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class LogTest : TokenTest() {
    @Test
    fun getToken() {
        Log.token shouldBe "log"
    }

    @Test
    fun apply() {
        test(Log, 0.0) shouldBe 0.0
        test(Log, 1.0) shouldBe 0.0
        test(Log, Math.E) shouldBe 1.0
        test(Log, Math.E * Math.E) shouldBe 2.0
    }
}
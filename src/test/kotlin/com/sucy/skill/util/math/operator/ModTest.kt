package com.sucy.skill.util.math.operator

import com.sucy.skill.util.math.TokenTest
import com.sucy.skill.util.math.formula.operator.Mod
import io.kotlintest.matchers.plusOrMinus
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class ModTest : TokenTest() {
    @Test
    fun getToken() {
        Mod.token shouldBe '%'
    }

    @Test
    fun apply() {
        test(Mod, 2.0, 4.0) shouldBe(2.0).plusOrMinus(EPSILON)
        test(Mod, 4.0, 2.0) shouldBe(0.0).plusOrMinus(EPSILON)
        test(Mod, 7.0, 4.0) shouldBe(3.0).plusOrMinus(EPSILON)
        test(Mod, 1.23, 1.0) shouldBe(0.23).plusOrMinus(EPSILON)
    }
}
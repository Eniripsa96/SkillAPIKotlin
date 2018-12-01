package com.sucy.skill

import org.junit.Assert

/**
 * SkillAPIKotlin © 2018
 */
object Assert {

    fun assertEquals(expected: Any, result: Any) {
        Assert.assertEquals(expected, result)
    }

    fun assertEquals(expected: Double, result: Double, delta: Double = 1e-10) {
        if (Math.abs(expected - result) > delta) {
            Assert.fail("Expected $result to equal $expected")
        }
    }
}

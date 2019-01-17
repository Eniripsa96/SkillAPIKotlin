package com.sucy.skill.util.math.formula.function

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Sq : Func {
    override val token = "sq"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val value = stack.pop()
        stack.push(value * value)
    }
}
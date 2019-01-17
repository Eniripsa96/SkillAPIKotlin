package com.sucy.skill.util.math.formula.function

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Sqrt : Func {
    override val token = "sqrt"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.sqrt(stack.pop()))
    }
}
package com.sucy.skill.util.math.formula.function

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Abs : Func {
    override val token = "abs"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.abs(stack.pop()))
    }
}
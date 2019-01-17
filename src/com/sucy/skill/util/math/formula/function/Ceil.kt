package com.sucy.skill.util.math.formula.function

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Ceil : Func {
    override val token = "ceil"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.ceil(stack.pop()))
    }
}
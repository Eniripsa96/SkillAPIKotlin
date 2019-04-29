package com.sucy.skill.util.math.formula.function

import java.util.*
import kotlin.math.max

/**
 * SkillAPIKotlin © 2018
 */
object Sqrt : Func {
    override val token = "sqrt"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.sqrt(max(0.0, stack.pop())))
    }
}
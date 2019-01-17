package com.sucy.skill.util.math.formula.function

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Floor : Func {
    override val token = "floor"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.floor(stack.pop()))
    }
}
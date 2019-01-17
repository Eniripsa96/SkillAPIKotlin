package com.sucy.skill.util.math.formula.function

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Log : Func {
    override val token = "log"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.log(Math.max(1.0, stack.pop())))
    }
}
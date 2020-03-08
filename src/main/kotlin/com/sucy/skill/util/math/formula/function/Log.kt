package com.sucy.skill.util.math.formula.function

import java.util.*
import kotlin.math.max

/**
 * SkillAPIKotlin © 2018
 */
object Log : Func {
    override val token = "log"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.log(max(1e-10, stack.pop())))
    }
}
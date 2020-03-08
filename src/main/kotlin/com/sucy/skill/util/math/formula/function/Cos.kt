package com.sucy.skill.util.math.formula.function

import com.sucy.skill.util.math.toRadians
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Cos : Func {
    override val token = "cos"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.cos(stack.pop().toRadians()))
    }
}
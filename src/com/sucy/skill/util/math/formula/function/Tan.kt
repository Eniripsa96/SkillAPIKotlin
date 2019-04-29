package com.sucy.skill.util.math.formula.function

import com.sucy.skill.util.math.toRadians
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Tan : Func {
    override val token = "tan"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.tan(stack.pop().toRadians()))
    }
}
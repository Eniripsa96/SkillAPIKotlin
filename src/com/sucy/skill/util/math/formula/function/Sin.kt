package com.sucy.skill.util.math.formula.function

import com.sucy.skill.util.math.formula.MathConst
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Sin : Func {
    override val token = "sin"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.sin(stack.pop() * MathConst.DEG_TO_RAD))
    }
}
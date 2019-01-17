package com.sucy.skill.util.math.formula.function

import com.sucy.skill.util.math.formula.MathConst
import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Cos : Func {
    override val token = "cos"
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(Math.cos(stack.pop() * MathConst.DEG_TO_RAD))
    }
}
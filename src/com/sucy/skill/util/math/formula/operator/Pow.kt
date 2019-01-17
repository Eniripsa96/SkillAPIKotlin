package com.sucy.skill.util.math.formula.operator

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Pow : Operator {
    override val token = '^'
    override val precedence = 3
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val right = stack.pop()
        stack.push(Math.pow(stack.pop(), right))
    }
}
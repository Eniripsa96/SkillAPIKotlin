package com.sucy.skill.util.math.formula.operator

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Minus : Operator {
    override val token = '-'
    override val precedence = 1
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val right = stack.pop()
        stack.push(stack.pop() - right)
    }
}
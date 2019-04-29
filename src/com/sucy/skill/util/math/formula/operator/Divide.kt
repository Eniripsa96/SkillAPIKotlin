package com.sucy.skill.util.math.formula.operator

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Divide : Operator {
    override val token = '/'
    override val precedence = 2
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        val right = stack.pop()
        if (right == 0.0) {
            stack.push(0.0)
        } else {
            stack.push(stack.pop() / right)
        }
    }
}
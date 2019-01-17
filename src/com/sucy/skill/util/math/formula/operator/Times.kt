package com.sucy.skill.util.math.formula.operator

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Times : Operator {
    override val token = '*'
    override val precedence = 2
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        stack.push(stack.pop() * stack.pop())
    }
}
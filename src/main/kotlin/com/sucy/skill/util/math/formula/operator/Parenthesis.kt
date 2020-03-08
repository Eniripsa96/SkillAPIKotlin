package com.sucy.skill.util.math.formula.operator

import java.util.*

/**
 * SkillAPIKotlin © 2018
 */
object Parenthesis : Operator {
    override val token = '('
    override val precedence = 0
    override fun apply(stack: Stack<Double>, values: DoubleArray) {
        throw UnsupportedOperationException("Parenthesis can't be evaluated")
    }
}